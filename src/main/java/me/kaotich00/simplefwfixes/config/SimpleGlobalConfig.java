package me.kaotich00.simplefwfixes.config;

import me.kaotich00.simplefwfixes.api.config.GlobalConfig;
import me.kaotich00.simplefwfixes.api.config.category.EnderpearlCategory;
import me.kaotich00.simplefwfixes.config.category.SimpleEnderpearlCategory;
import ninja.leaping.configurate.objectmapping.Setting;

public class SimpleGlobalConfig implements GlobalConfig {

    @Setting(comment =
            " ******************************************************** \n" +
            " |                         Enderpearl                   | \n" +
            " ********************************************************   "
    )
    private SimpleEnderpearlCategory enderpearl = new SimpleEnderpearlCategory();

    @Override
    public EnderpearlCategory getEnderpearlCategory() {
        return this.enderpearl;
    }

}
