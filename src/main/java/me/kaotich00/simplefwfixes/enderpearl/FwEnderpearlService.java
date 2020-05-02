package me.kaotich00.simplefwfixes.enderpearl;

import me.kaotich00.simplefwfixes.api.config.ConfigService;
import me.kaotich00.simplefwfixes.api.config.category.EnderpearlCategory;
import me.kaotich00.simplefwfixes.api.enderpearl.EnderpearlService;
import org.spongepowered.api.Sponge;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class FwEnderpearlService implements EnderpearlService {

    private HashMap<UUID,Long> cooldownList = new HashMap<>();

    @Override
    public long getRemainingSeconds(UUID uuid) {
        // Compute difference in seconds since the last throw
        final long now = System.currentTimeMillis();
        final long lastUsageTime = cooldownList.get( uuid );
        final long diff = now - lastUsageTime;

        // Get enderpearl cooldown from configs
        final EnderpearlCategory cc = Sponge.getServiceManager().provideUnchecked(ConfigService.class).
                getGlobal().
                getEnderpearlCategory();
        final long cooldown = cc.getCooldownSeconds();

        // Negative values means the cooldown expired
        if (TimeUnit.MILLISECONDS.toSeconds(diff) > cooldown) {
            updateUserCooldown( uuid );
            return 0;
        }

        return TimeUnit.MILLISECONDS.toSeconds(diff);
    }

    @Override
    public Boolean isInCooldown(UUID uuid) {
        return this.cooldownList.containsKey(uuid);
    }

    @Override
    public void updateUserCooldown(UUID uuid) {
        this.cooldownList.put( uuid, System.currentTimeMillis() );
    }
}
