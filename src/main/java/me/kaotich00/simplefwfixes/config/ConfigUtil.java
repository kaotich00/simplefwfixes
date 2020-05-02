package me.kaotich00.simplefwfixes.config;

import me.kaotich00.simplefwfixes.Simplefwfixes;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigRoot;
import org.spongepowered.api.plugin.PluginContainer;

import java.nio.file.Path;

public final class ConfigUtil {

    static {
        final PluginContainer plugin = Sponge.getPluginManager().getPlugin(Simplefwfixes.ID).get();
        final ConfigRoot simplefwfixes = Sponge.getConfigManager().getPluginConfig(plugin);

        CONF = simplefwfixes.getConfigPath();
    }

    public static final Path CONF;

    private ConfigUtil() {}
}
