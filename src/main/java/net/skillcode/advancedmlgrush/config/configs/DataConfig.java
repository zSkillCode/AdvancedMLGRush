package net.skillcode.advancedmlgrush.config.configs;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.Configurable;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
public class DataConfig extends Configurable {

    public static final String USE_MYSQL = "use_mysql";
    public static final String MYSQL_HOST = "mysql_host";
    public static final String MYSQL_PORT = "mysql_port";
    public static final String MYSQL_DATABASE = "mysql_database";
    public static final String MYSQL_USER = "mysql_user";
    public static final String MYSQL_PASSWORD = "mysql_password";
    public static final String ASYNC_UPDATE_PERIOD = "async_update_period";

    @PostConstruct
    public void initConfig() {
        super.init();
    }

    @Override
    protected String filePath() {
        return Constants.DATA_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(USE_MYSQL, false));
        list.add(new Pair<>(MYSQL_HOST, "localhost"));
        list.add(new Pair<>(MYSQL_PORT, "3306"));
        list.add(new Pair<>(MYSQL_DATABASE, "database"));
        list.add(new Pair<>(MYSQL_USER, "root"));
        list.add(new Pair<>(MYSQL_PASSWORD, "password"));
        list.add(new Pair<>(ASYNC_UPDATE_PERIOD, 10L));
    }
}