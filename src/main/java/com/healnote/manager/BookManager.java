package com.healnote.manager;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

/**
 * Manages special books recognition and creation
 */
public class BookManager {
    public static final String DEATH_NOTE_NAME = "Death Note";
    public static final String HEAL_NOTE_NAME = "Heal Note";
    public static final String DEBAN_BOOK_NAME = "Deban Book";

    /**
     * Get the type of book
     * @return "death", "heal", "deban", or null if not a special book
     */
    public static String getBookType(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return null;
        }

        BookMeta meta = (BookMeta) item.getItemMeta();
        String displayName = meta.getDisplayName();

        if (displayName.equals(DEATH_NOTE_NAME)) {
            return "death";
        } else if (displayName.equals(HEAL_NOTE_NAME)) {
            return "heal";
        } else if (displayName.equals(DEBAN_BOOK_NAME)) {
            return "deban";
        }

        return null;
    }

    /**
     * Check if item is a Death Note
     */
    public static boolean isDeathNote(ItemStack item) {
        return "death".equals(getBookType(item));
    }

    /**
     * Check if item is a Heal Note
     */
    public static boolean isHealNote(ItemStack item) {
        return "heal".equals(getBookType(item));
    }

    /**
     * Check if item is a Deban Book
     */
    public static boolean isDebanBook(ItemStack item) {
        return "deban".equals(getBookType(item));
    }

    /**
     * Check if item is any special book
     */
    public static boolean isSpecialBook(ItemStack item) {
        return getBookType(item) != null;
    }
}
