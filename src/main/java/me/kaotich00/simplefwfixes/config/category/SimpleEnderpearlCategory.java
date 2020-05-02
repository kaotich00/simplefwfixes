package me.kaotich00.simplefwfixes.config.category;

import me.kaotich00.simplefwfixes.api.config.category.EnderpearlCategory;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class SimpleEnderpearlCategory implements EnderpearlCategory {

    @Setting(value = "cooldown-time", comment = "Enderpearl cooldown (seconds) between each use")
    private long calculateRecidivism = 15;

    @Override
    public long getCooldownSeconds() {
        return this.calculateRecidivism;
    }

}
