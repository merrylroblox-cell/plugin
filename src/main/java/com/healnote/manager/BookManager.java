package com.healnote.manager;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Manages special books recognition and creation
 * Uses PersistentDataContainer for secure identification
 */
public class BookManager {
    public static final String DEATH_NOTE_NAME = "Death Note";
    public static final String HEAL_NOTE_NAME = "Heal Note";
    public static final String DEBAN_BOOK_NAME = "Deban Book";
    
    private final JavaPlugin plugin;
    private final NamespacedKey deathNoteKey;
    private final NamespacedKey healNoteKey;
    private final NamespacedKey debanBookKey;

    public BookManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.deathNoteKey = new NamespacedKey(plugin, "death_note_id");
        this.healNoteKey = new NamespacedKey(plugin, "heal_note_id");
        this.debanBookKey = new NamespacedKey(plugin, "deban_book_id");
    }

    /**
     * Get the type of book using PersistentDataContainer
     * @return "death", "heal", "deban", or null if not a special book
     */
    public String getBookType(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return null;
        }

        BookMeta meta = (BookMeta) item.getItemMeta();
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();

        // Check using PersistentDataContainer (secure)
        if (dataContainer.has(deathNoteKey, PersistentDataType.STRING)) {
            String value = dataContainer.get(deathNoteKey, PersistentDataType.STRING);
            if ("death_note".equals(value)) {
                return "death";
            }
        }

        if (dataContainer.has(healNoteKey, PersistentDataType.STRING)) {
            String value = dataContainer.get(healNoteKey, PersistentDataType.STRING);
            if ("heal_note".equals(value)) {
                return "heal";
            }
        }

        if (dataContainer.has(debanBookKey, PersistentDataType.STRING)) {
            String value = dataContainer.get(debanBookKey, PersistentDataType.STRING);
            if ("deban_book".equals(value)) {
                return "deban";
            }
        }

        // Fallback to display name check (for backwards compatibility)
        String displayName = meta.getDisplayName();
        if (displayName != null) {
            if (displayName.equals(DEATH_NOTE_NAME) || displayName.equals("§c§lDeath Note")) {
                return "death";
            } else if (displayName.equals(HEAL_NOTE_NAME) || displayName.equals("§a§lHeal Note")) {
                return "heal";
            } else if (displayName.equals(DEBAN_BOOK_NAME) || displayName.equals("§d§lDeban Book")) {
                return "deban";
            }
        }

        return null;
    }

    /**
     * Check if item is a Death Note
     */
    public boolean isDeathNote(ItemStack item) {
        return "death".equals(getBookType(item));
    }

    /**
     * Check if item is a Heal Note
     */
    public boolean isHealNote(ItemStack item) {
        return "heal".equals(getBookType(item));
    }

    /**
     * Check if item is a Deban Book
     */
    public boolean isDebanBook(ItemStack item) {
        return "deban".equals(getBookType(item));
    }

    /**
     * Check if item is any special book
     */
    public boolean isSpecialBook(ItemStack item) {
        return getBookType(item) != null;
    }
}
