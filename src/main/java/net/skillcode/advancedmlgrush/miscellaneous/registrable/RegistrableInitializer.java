package net.skillcode.advancedmlgrush.miscellaneous.registrable;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.game.buildmode.BuildModeManager;
import net.skillcode.advancedmlgrush.inventory.InventoryManager;
import net.skillcode.advancedmlgrush.util.Initializer;
import org.jetbrains.annotations.NotNull;

@Singleton
public class RegistrableInitializer implements Initializer {

    private final RegistrableManager manager;

    @Inject
    public RegistrableInitializer(final @NotNull RegistrableManager manager) {
        this.manager = manager;
    }

    @Override
    public void init(final @NotNull Injector injector) {
        manager.registerRegistrable(injector.getInstance(BuildModeManager.class));
        manager.registerRegistrable(injector.getInstance(InventoryManager.class));
    }
}