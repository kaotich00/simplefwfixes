package me.kaotich00.simplefwfixes.api.enderpearl;

import java.time.Instant;
import java.util.UUID;

public interface EnderpearlService {

    long getRemainingSeconds( UUID uuid );

    Boolean isInCooldown( UUID uuid );

    void updateUserCooldown( UUID uuid );

}
