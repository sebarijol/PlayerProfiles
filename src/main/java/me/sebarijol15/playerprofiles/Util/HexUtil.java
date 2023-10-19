package me.sebarijol15.playerprofiles.Util;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexUtil {
    // Regular expression pattern for hex codes
    public static final Pattern HEX_PATTERN = Pattern.compile("&#(\\w{5}[0-9a-f])");

    /**
     * Translates hex codes in a given text into Minecraft color codes.
     *
     * @param textToTranslate The text to translate.
     * @return The translated text with Minecraft color codes.
     */
    public String translateHexCodes(String textToTranslate) {
        Matcher matcher = HEX_PATTERN.matcher(textToTranslate);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            // Replace hex codes with corresponding ChatColor
            matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
        }

        // Translate '&' color codes
        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }
}