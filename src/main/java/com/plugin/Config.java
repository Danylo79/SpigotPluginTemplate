package com.plugin;

import com.plugin.files.ConfigFile;

public class Config {
    private static ConfigFile config;

    public Config(Main plugin) {
        config = new ConfigFile("config.yml", plugin.getDataFolder(), plugin.getLogger(), plugin);
    }

    public static ConfigFile getConfig() {
        return config;
    }
}
