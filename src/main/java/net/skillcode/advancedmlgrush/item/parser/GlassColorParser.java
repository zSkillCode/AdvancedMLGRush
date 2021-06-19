package net.skillcode.advancedmlgrush.item.parser;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.item.GlassColor;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.EnumUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@Singleton
public class GlassColorParser {

    private final EnumUtils enumUtils;
    private final JavaPlugin javaPlugin;

    @Inject
    public GlassColorParser(final @NotNull EnumUtils enumUtils, final @NotNull JavaPlugin javaPlugin) {
        this.enumUtils = enumUtils;
        this.javaPlugin = javaPlugin;
    }

    public GlassColor parse(final @NotNull String input) {
        GlassColor glassColor = GlassColor.GRAY;

        if (enumUtils.isInEnum(GlassColor.class, input)) {
            glassColor = GlassColor.valueOf(input);
        } else {
            javaPlugin.getLogger().warning(String.format(Constants.MATERIAL_PARSE_ERROR, input));
        }

        return glassColor;
    }

}