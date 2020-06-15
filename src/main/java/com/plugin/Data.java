package com.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class Data {
    private static Data instance = new Data(JavaPlugin.getPlugin(Main.class));
    private Data(Main plugin) {

    }

    public static Data getInstance() {
        return instance;
    }
}
