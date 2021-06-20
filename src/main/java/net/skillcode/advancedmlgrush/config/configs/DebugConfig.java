package net.skillcode.advancedmlgrush.config.configs;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.Configurable;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
public class DebugConfig extends Configurable {

    public static final String LOG_STACKTRACES = "log_stacktraces";
    public static final String LOG_QUERIES = "log_queries";

    @PostConstruct
    public void initConfig() {
        super.init();
    }

    @Override
    protected String filePath() {
        return Constants.DEBUG_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(LOG_STACKTRACES, true));
        list.add(new Pair<>(LOG_QUERIES, false));
    }
}