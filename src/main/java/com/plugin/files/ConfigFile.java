package com.plugin.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigFile {
    private File dataFolder;
    private Logger logger;
    private IResourceManager resourceManager;
    private FileConfiguration dataConfig = null;
    private File configFile = null;
    private String fileName;

    public ConfigFile(String fileName, File dataFolder, Logger logger, IResourceManager resourceManager) {
        this.dataFolder = dataFolder;
        this.logger = logger;
        this.resourceManager = resourceManager;
        this.fileName = fileName;
        logger.log(Level.INFO, "Yaml Config for " + fileName);
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.configFile == null) {
            this.configFile = new File(dataFolder, fileName);
        }

        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);
    }

    public void set(String key, Object value) {
        this.dataConfig.set(key, value);
        this.saveConfig();
    }

    private FileConfiguration getConfig() {
        if (this.dataConfig == null)
            reloadConfig();
        return this.dataConfig;
    }

    public String getString(String path) {
        return this.getConfig().getString(path);
    }

    public Integer getInt(String path) {
        return getConfig().getInt(path);
    }

    public Boolean getBoolean(String path) {
        return getConfig().getBoolean(path);
    }

    public void saveConfig() {
        logger.log(Level.INFO, "Config Saving!");
        if (this.dataConfig == null || this.configFile == null) {
            return;
        }
        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not save config to " + this.configFile, e);
        }
    }

    private void saveDefaultConfig() {
        if (this.configFile == null) {
            this.configFile = new File(dataFolder, this.fileName);
        }

        if (!this.configFile.exists()) {
            this.resourceManager.saveResource(this.fileName, false);
        }
    }
}
