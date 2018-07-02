package com.doctordark.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Color {
    public static String translate(final String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static List<String> translateFromArray(final List<String> text) {
        final List<String> messages = new ArrayList<String>();
        for (final String string : text) {
            messages.add(translate(string));
        }
        return messages;
    }
}

