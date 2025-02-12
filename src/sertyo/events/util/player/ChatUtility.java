package sertyo.events.util.player;


import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import sertyo.events.Main;
import sertyo.events.util.Util;

public class ChatUtility implements Util {
    public static String chatPrefix;

    public static void addChatMessage(String message) {
        mc.player.sendMessage(new StringTextComponent(chatPrefix + message), null);
    }

    static {
        chatPrefix = TextFormatting.DARK_GRAY + "[" + TextFormatting.GOLD + TextFormatting.BOLD + Main.name + TextFormatting.DARK_GRAY + "] >> " + TextFormatting.RESET;
    }
}
