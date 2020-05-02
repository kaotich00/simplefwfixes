package me.kaotich00.simplefwfixes.utils;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class SimpleNotificationUtils {

    public static Text formatBanMessage( String message ){
        return Text.of( TextColors.GOLD, "[ForgottenWorld] ", TextColors.RED, message );
    }

}

