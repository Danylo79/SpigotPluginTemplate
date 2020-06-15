package com.plugin;

import com.plugin.commands.SimpleCommand;
import com.plugin.files.IResourceManager;
import com.plugin.utils.Utils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener, IResourceManager {

    private Utils utils;

    @Override
    public void onEnable() {
        init();
        utils.log("&aEnabled " + this.getName());
    }

    @Override
    public void onDisable() {
        utils.log("&cDisabled " + this.getName());
    }

    public void init() {
        utils = new Utils(this, Config.getConfig());

        registerEvents(this, this);

        setCommandExecutor("hello", new SimpleCommand());
    }

    public void registerEvents(Listener listener, Plugin plugin) {
        this.getServer().getPluginManager().registerEvents(listener, plugin);
        utils.log("&aRegistered events for " + listener.getClass());
    }

    public void setCommandExecutor(String name, CommandExecutor executor) {
        this.getServer().getPluginCommand(name).setExecutor(executor);
        utils.log("&aSet command executor for " + name + " to " + executor.getClass());
    }
}
