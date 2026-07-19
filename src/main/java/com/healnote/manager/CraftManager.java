package com.healnote.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Manages crafting recipes for special items
 */
public class CraftManager {
    private final JavaPlugin plugin;

    public CraftManager(JavaPlugin plugin) {
        this.plugin = plugin;
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
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createHealNote() {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§a§lHeal Note");
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createDebanBook() {
        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§d§lDeban Book");
        item.setItemMeta(meta);
        return item;
    }
}
