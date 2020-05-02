package me.kaotich00.simplefwfixes.enderpearl.listeners;

import me.kaotich00.simplefwfixes.Simplefwfixes;
import me.kaotich00.simplefwfixes.api.config.ConfigService;
import me.kaotich00.simplefwfixes.api.config.category.EnderpearlCategory;
import me.kaotich00.simplefwfixes.api.enderpearl.EnderpearlService;
import me.kaotich00.simplefwfixes.utils.SimpleNotificationUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.entity.projectile.LaunchProjectileEvent;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import java.util.Optional;

public class FwEnderpearlListener {

    private Simplefwfixes plugin;

    public FwEnderpearlListener(Simplefwfixes plugin) {
        this.plugin = plugin;
    }

    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onEnderpearlThrow(LaunchProjectileEvent event) {

        plugin.getLogger().info("Proiettile lanciato e registrato");

    }

    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onPlayerItemInteraction(InteractItemEvent.Secondary event){

        // Player who caused the event to trigger
        Optional<Player> optPlayer = event.getCause().first(Player.class);

        // Snapshot of the event
        ItemStackSnapshot itemSnapshot = event.getItemStack();

        // Check if it is an enderpearl
        if(itemSnapshot.getType() == ItemTypes.ENDER_PEARL && optPlayer.isPresent()) {
            Player thrower = optPlayer.get();

            final EnderpearlService enderpearlService = Sponge.getServiceManager().provideUnchecked(EnderpearlService.class);
            final EnderpearlCategory cc = Sponge.getServiceManager().provideUnchecked(ConfigService.class).
                    getGlobal().
                    getEnderpearlCategory();
            final long cooldown = cc.getCooldownSeconds();

            if (enderpearlService.isInCooldown( thrower.getUniqueId() )) {
                long timeElapsed = enderpearlService.getRemainingSeconds(thrower.getUniqueId());
                if (timeElapsed > 0) {
                    final String message = "Puoi riutilizzare l'item fra " + (cooldown - timeElapsed) + " secondi";
                    thrower.sendMessage(SimpleNotificationUtils.formatBanMessage(message));
                    event.setCancelled(true);
                }
            } else {
                enderpearlService.updateUserCooldown(thrower.getUniqueId());
            }
        }

    }

}
