package net.skillcode.advancedmlgrush.event;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.item.items.handlers.ChallengerHandler;
import net.skillcode.advancedmlgrush.util.Initializer;
import org.jetbrains.annotations.NotNull;

@Singleton
public class EventHandlerInitializer implements Initializer {

    private final EventManager eventManager;

    @Inject
    public EventHandlerInitializer(final @NotNull EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void init(final @NotNull Injector injector) {
        eventManager.registerEventListeners(injector.getInstance(ChallengerHandler.class));
    }
}
