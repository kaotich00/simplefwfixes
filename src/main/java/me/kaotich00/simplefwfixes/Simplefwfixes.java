package me.kaotich00.simplefwfixes;

import com.google.inject.Inject;
import me.kaotich00.simplefwfixes.api.config.ConfigService;
import me.kaotich00.simplefwfixes.api.enderpearl.EnderpearlService;
import me.kaotich00.simplefwfixes.config.SimpleConfigurationService;
import me.kaotich00.simplefwfixes.enderpearl.FwEnderpearlService;
import me.kaotich00.simplefwfixes.enderpearl.listeners.FwEnderpearlListener;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.IOException;

@Plugin(id = Simplefwfixes.ID, name = Simplefwfixes.NAME, version = Simplefwfixes.VERSION, description = Simplefwfixes.DESCRIPTION)
public class Simplefwfixes {

    public static final String ID = "simplefwfixes";
    public static final String NAME = "Simplefwfixes";
    public static final String VERSION = "0.1";
    public static final String DESCRIPTION = "Simple plugin for various ForgottenWorld fixes and implementations";

    @Inject private PluginContainer container;

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("Succussfully loaded [" + NAME + "] version " + VERSION );
    }

    @Listener
    public void onPreInit(GamePreInitializationEvent event ) {
        Sponge.getServiceManager().setProvider( this.container, EnderpearlService.class, new FwEnderpearlService() );
    }

    @Listener
    public void onInit(GameInitializationEvent event) {
        try {
            registerConfigService();

            Sponge.getServiceManager().provideUnchecked(ConfigService.class)
                    .reload()
                    .get();
        } catch (Exception e) {
            logger.info("[" + NAME + "] Error: " + e.toString() );
            return;
        }
        registerListeners();
    }

    private void registerListeners() {
        Sponge.getEventManager().registerListeners(this.container, new FwEnderpearlListener(this));
        logger.info("[" + NAME + "] Listener registrati con successo" );
    }

    @Listener
    public void onServerStop(GameStoppingServerEvent event){
        Sponge.getServiceManager().provideUnchecked(ConfigService.class)
                .save();
    }

    private void registerConfigService() throws IOException, ObjectMappingException {
        final SimpleConfigurationService cs = new SimpleConfigurationService( this );
        cs.populate();
        Sponge.getServiceManager().setProvider(this.container, ConfigService.class, cs);
    }

    public Logger getLogger() {
        return logger;
    }
}
