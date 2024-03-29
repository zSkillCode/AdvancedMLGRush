/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This file may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: http://discord.skillplugins.com
 */

package com.skillplugins.advancedmlgrush.sql;

import com.google.inject.Inject;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.config.configs.DebugConfig;
import com.skillplugins.advancedmlgrush.exception.ExceptionHandler;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class DataSaver {

    @Inject
    protected ExceptionHandler exceptionHandler;
    @Inject
    protected ThreadPoolManager threadPoolManager;
    @Inject
    private JavaPlugin javaPlugin;
    @Inject
    private ConnectionManager connectionManager;
    @Inject
    private DebugConfig debugConfig;

    private DataSaverParams params;

    @Nullable
    private Connection connection;

    @PostConstruct
    public void init() {
        params = initParams();
        checkConnection();
        initTable();
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException throwables) {
            exceptionHandler.handleUnexpected(throwables);
        }
        return false;
    }

    protected void executeUpdateSync(final @NotNull String query) {
        if (debugConfig.getBoolean(DebugConfig.LOG_QUERIES)) {
            javaPlugin.getLogger().info(String.format(Constants.QUERY_MESSAGE, query));
        }
        if (checkConnection()) {
            try (final PreparedStatement preparedStatement = connection.prepareStatement(replaceName(query))) {
                preparedStatement.executeUpdate();
            } catch (SQLException exception) {
                exceptionHandler.handleUnexpected(exception);
            }
        }
    }

    protected void executeUpdateAsync(final @NotNull String query) {
        threadPoolManager.submit(() -> executeUpdateSync(query));
    }

    protected Optional<ResultSet> executeQuerySync(final @NotNull String query) {
        if (debugConfig.getBoolean(DebugConfig.LOG_QUERIES)) {
            javaPlugin.getLogger().info(String.format(Constants.QUERY_MESSAGE, query));
        }
        if (checkConnection()) {
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement(replaceName(query));
                final ResultSet resultSet = preparedStatement.executeQuery();
                return Optional.of(resultSet);
            } catch (SQLException exception) {
                exceptionHandler.handleUnexpected(exception);
            }
        }
        return Optional.empty();
    }

    protected void executeQueryAsync(final @NotNull String query, final @NotNull Callback callback) {
        if (checkConnection()) {
            final CompletableFuture<Optional<ResultSet>> future = CompletableFuture.supplyAsync(() -> executeQuerySync(query), threadPoolManager.getThreadPool());
            future.whenComplete((optional, throwable) -> {
                try {
                    if (optional.isPresent()) {
                        callback.onSuccess(optional.get());
                    } else {
                        callback.onFailure(Optional.empty());
                    }
                } catch (SQLException exception) {
                    exceptionHandler.handleUnexpected(exception);
                    callback.onFailure(Optional.of(exception));
                }
            });
        } else {
            callback.onFailure(Optional.empty());
        }
    }

    protected abstract DataSaverParams initParams();

    protected abstract String creationQuery();

    //column name, value
    protected abstract List<Pair<String, String>> columns(final @NotNull List<Pair<String, String>> columns);

    private boolean checkConnection() {
        if (!isConnected()) {
            createConnection();
        }
        return isConnected();
    }

    private void initTable() {
        if (isConnected()) {
            executeUpdateSync(creationQuery());
            final List<Pair<String, String>> columns = columns(new ArrayList<>());
            try {
                final DatabaseMetaData databaseMetaData = connection.getMetaData();

                for (final Pair<String, String> pair : columns) {
                    final String columnName = pair.getKey();
                    final String value = pair.getValue();

                    final ResultSet resultSet = databaseMetaData.getColumns(null, null,
                            replaceName("{name}"), columnName);

                    if (resultSet != null) {
                        if (!resultSet.next()) {
                            executeUpdateSync(String.format("ALTER TABLE {name} ADD COLUMN %s;", columnName + " " + value));
                        }
                    }
                }
            } catch (SQLException sqlException) {
                exceptionHandler.handleUnexpected(sqlException);
            }
        }
    }

    private void createConnection() {
        connection = params.isMySQL() ? createMySQLConnection() : createSQLiteConnection();
        connectionManager.addConnection(connection);
    }

    @Nullable
    private Connection createMySQLConnection() {
        try {
            final String url = "jdbc:mysql://" + params.getHost() + ":" + checkPort(params.getPort()) + "/" + params.getDatabase();

            return DriverManager.getConnection(url, params.getUser(), params.getPassword());
        } catch (SQLException throwables) {
            exceptionHandler.handleUnexpected(throwables);
        }
        return null;
    }

    @Nullable
    private Connection createSQLiteConnection() {
        try {
            Class.forName("org.sqlite.JDBC");

            final File file = new File(params.getDataFilePath());
            file.getParentFile().mkdirs();

            return DriverManager.getConnection("jdbc:sqlite:" + file.getPath());
        } catch (SQLException | ClassNotFoundException throwables) {
            exceptionHandler.handleUnexpected(throwables);
        }
        return null;
    }

    private String checkPort(final @NotNull String port) {
        String finalPort = port;
        try {
            Integer.parseInt(port);
        } catch (final NumberFormatException exception) {
            finalPort = "3306";
            javaPlugin.getLogger().warning(Constants.INVALID_PORT_MESSAGE);
        }
        return finalPort;
    }

    private String replaceName(final @NotNull String query) {
        return query.replace("{name}", params.getTable());
    }

    public interface Callback {

        void onSuccess(final @NotNull ResultSet resultSet) throws SQLException;

        void onFailure(final @NotNull Optional<Exception> optional);

    }
}
