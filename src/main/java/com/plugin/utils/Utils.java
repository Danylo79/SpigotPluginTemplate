package com.plugin.utils;

import com.plugin.files.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private JavaPlugin plugin;
    private ConfigFile config;
    private static Utils instance;

    public Utils(JavaPlugin plugin, ConfigFile config) {
        this.plugin = plugin;
        this.config = config;
        instance = new Utils(plugin, config);
    }

    public static Utils getInstance() {
        return instance;
    }

    public void log(String message) {
        plugin.getServer().getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', getPrefix() + " " + message));
    }

    public String getPrefix() {
        return this.config.getString("prefix");
    }

    public Double percOf(Integer number1, Integer prec) {
        return number1 * prec / 100.0;
    }

    public Double percOf(Double number1, Integer prec) {
        return number1 * prec / 100.0;
    }

    public static void copyDirectory(File source, File destination) throws IOException {
        Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public int getRandom(int min, int max) {
        int range = max - min + 1;
        return (int) (Math.random() * range) + min;
    }

    public static void createDefaultInv(ItemStack item, ItemMeta meta, List<String> lore, Inventory inv) {
        for (Integer i = 0; i < 54; i++) {
            item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            meta = item.getItemMeta();
            meta.setDisplayName("   ");
            lore = new ArrayList<String>();
            meta.setLore(lore);
            item.setItemMeta(meta);
            inv.setItem(i, item);
        }
    }
}
