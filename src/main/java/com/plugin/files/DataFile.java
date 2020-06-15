package com.plugin.files;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.plugin.files.utils.GsonFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataFile {
    private File file = null;
    private final Gson gson = new GsonFactory().create();
    private List<Map<String, Object>> map;
    private String fileName;
    private File dataFolder;
    private Logger logger;
    private IResourceManager resourceManager;

    public DataFile(String fileName, File dataFolder, Logger logger, IResourceManager resourceManager) {
        this.fileName = fileName;
        this.dataFolder = dataFolder;
        this.logger = logger;
        this.resourceManager = resourceManager;

        saveDefaultConfig();
    }

    public void reloadConfig() throws FileNotFoundException {
        logger.log(Level.INFO, "Reloading data file " + fileName);

        if (this.file == null) {
            this.file = new File(dataFolder, this.fileName);
        }

        map = gson.fromJson(new FileReader(file), new TypeToken<List<Map<String, Object>>>() {}.getType());
    }

    public List<Map<String, Object>> getItems() {
        logger.log(Level.INFO, "Getting data items");
        if (this.map == null) {
            try {
                logger.log(Level.INFO, "Map is null, call reload");
                reloadConfig();
            } catch (FileNotFoundException e) {
                logger.log(Level.SEVERE, "Failed to reload data file");
                e.printStackTrace();
            }
        }
        return this.map;
    }

    public void addItem(Map<String, Object> item) {
        List<Map<String, Object>> items = getItems();
        items.add(item);
        this.saveConfig();
    }

    public void saveConfig() {
        if (this.map == null || this.file == null) {
            logger.log(Level.WARNING, "Nothing to save to " + fileName);
            return;
        }
        try {
            logger.log(Level.INFO, "Saving data file " + fileName);
            final String json = gson.toJson(map);
            file.delete();
            Files.write(file.toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE); // java.nio.Files
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not save data file " + this.file, e);
        }
    }

    private void saveDefaultConfig() {
        logger.log(Level.INFO, "Storing default data file " + fileName);

        if (this.file == null) {
            this.file = new File(dataFolder, this.fileName);
        }

        if (!this.file.exists()) {
            this.resourceManager.saveResource(this.fileName, false);
        }
    }

    public Map<String, Object> find(String field, String query) {
        this.logger.log(Level.INFO, "Search field " + field + " by " + query);

        List<Map<String, Object>> items = this.getItems();
        this.logger.log(Level.INFO, "Size of the array is " + items.size());

        for (Integer j = 0; j < items.size(); j++) {
            Map<String, Object> item = items.get(j);
            this.logger.log(Level.INFO, "Item number " + j + " is " + item);

            if (item.get(field).toString().equalsIgnoreCase(query)) {
                this.logger.log(Level.INFO, "Found item " + item);
                return item;
            }
        }

        this.logger.log(Level.WARNING, "Not found item!");
        return null;
    }
}
