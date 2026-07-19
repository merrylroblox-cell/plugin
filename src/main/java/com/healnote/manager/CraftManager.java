package com.healnote.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Manages crafting recipes for special items
 */
public class CraftManager {
    private final JavaPlugin plugin;
    private final NamespacedKey deathNoteKey;
    private final NamespacedKey healNoteKey;
    private final NamespacedKey debanBookKey;
    private final NamespacedKey cursedPageKey;

    public CraftManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.deathNoteKey = new NamespacedKey(plugin, "death_note_id");
        this.healNoteKey = new NamespacedKey(plugin, "heal_note_id");
        this.debanBookKey = new NamespacedKey(plugin, "deban_book_id");
        this.cursedPageKey = new NamespacedKey(plugin, "cursed_page_id");
        registerRecipes();
    }

    private void registerRecipes() {
        // Cursed Page recipe
        ShapedRecipe cursedPageRecipe = new ShapedRecipe(
            new NamespacedKey(plugin, "cursed_page"),
            createCursedPage()
        );
        cursedPageRecipe.shape(
            "NSN",
            "SPS",
            "NSN"
        );
        cursedPageRecipe.setIngredient('N', Material.NETHER_STAR);
        cursedPageRecipe.setIngredient('S', Material.REINFORCED_DEEPSLATE);
        cursedPageRecipe.setIngredient('P', Material.PAPER);
        Bukkit.addRecipe(cursedPageRecipe);

        // Death Note recipe
        ShapedRecipe deathNoteRecipe = new ShapedRecipe(
            new NamespacedKey(plugin, "death_note"),
            createDeathNote()
        );
        deathNoteRecipe.shape(
            "TPT",
            "PBP",
            "TPT"
        );
        deathNoteRecipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
        deathNoteRecipe.setIngredient('P', Material.PAPER); // Represents Cursed Page
        deathNoteRecipe.setIngredient('B', Material.WRITABLE_BOOK);
        Bukkit.addRecipe(deathNoteRecipe);

        // Heal Note recipe
        ShapedRecipe healNoteRecipe = new ShapedRecipe(
            new NamespacedKey(plugin, "heal_note"),
            createHealNote()
        );
        healNoteRecipe.shape(
            "PTP",
            "TBT",
            "PTP"
        );
        healNoteRecipe.setIngredient('P', Material.PAPER); // Represents Cursed Page
        healNoteRecipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
        healNoteRecipe.setIngredient('B', Material.WRITABLE_BOOK);
        Bukkit.addRecipe(healNoteRecipe);

        // Deban Book recipe
        ShapedRecipe debanBookRecipe = new ShapedRecipe(
            new NamespacedKey(plugin, "deban_book"),
            createDebanBook()
        );
        debanBookRecipe.shape(
            "NSN",
            "SBS",
            "NSN"
        );
        debanBookRecipe.setIngredient('N', Material.NETHER_STAR);
        debanBookRecipe.setIngredient('S', Material.REINFORCED_DEEPSLATE);
        debanBookRecipe.setIngredient('B', Material.WRITABLE_BOOK);
        Bukkit.addRecipe(debanBookRecipe);
    }

    private ItemStack createCursedPage() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Page Maudite");
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createDeathNote() {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§c§lDeath Note");
        // Add PersistentDataContainer identifier
        meta.getPersistentDataContainer().set(deathNoteKey, PersistentDataType.STRING, "death_note");
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createHealNote() {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§a§lHeal Note");
        // Add PersistentDataContainer identifier
        meta.getPersistentDataContainer().set(healNoteKey, PersistentDataType.STRING, "heal_note");
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createDebanBook() {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§d§lDeban Book");
        // Add PersistentDataContainer identifier
        meta.getPersistentDataContainer().set(debanBookKey, PersistentDataType.STRING, "deban_book");
        item.setItemMeta(meta);
        return item;
    }

    // Getters for NamespacedKeys
    public NamespacedKey getDeathNoteKey() {
        return deathNoteKey;
    }

    public NamespacedKey getHealNoteKey() {
        return healNoteKey;
    }

    public NamespacedKey getDebanBookKey() {
        return debanBookKey;
    }

    public NamespacedKey getCursedPageKey() {
        return cursedPageKey;
    }
}
