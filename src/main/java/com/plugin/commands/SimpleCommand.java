package com.plugin.commands;

import com.plugin.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SimpleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (label.equalsIgnoreCase("hello")) {
                Player p = (Player) sender;
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&ch&ae&el&bl&6o&f!"));
            }
            return true;
        } else {
            Utils.getInstance().log("&aThe console cannot send this command!");
            return false;
        }
    }
}
