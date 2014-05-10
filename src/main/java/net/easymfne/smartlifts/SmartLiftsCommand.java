/*
 * This file is part of the SmartLifts plugin by EasyMFnE.
 * 
 * SmartLifts is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 * 
 * SmartLifts is distributed in the hope that it will be useful, but without any
 * warranty; without even the implied warranty of merchantability or fitness for
 * a particular purpose. See the GNU General Public License for details.
 * 
 * You should have received a copy of the GNU General Public License v3 along
 * with SmartLifts. If not, see <http://www.gnu.org/licenses/>.
 */
package net.easymfne.smartlifts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;

/**
 * The class that handles the "/smartlifts" command for the plugin.
 * 
 * Subcommands: reload
 * 
 * @author Eric Hildebrand
 */
public class SmartLiftsCommand implements CommandExecutor, TabExecutor {
    
    private SmartLifts plugin = null;
    private List<String> subcommands = null;
    
    /**
     * Instantiate by getting a reference to the plugin instance and registering
     * this class to handle the '/smartlifts' command.
     * 
     * @param plugin
     *            Reference to SmartLifts plugin instance
     */
    public SmartLiftsCommand(SmartLifts plugin) {
        this.plugin = plugin;
        subcommands = new ArrayList<String>();
        subcommands.add("reload");
        plugin.getCommand("smartlifts").setExecutor(this);
    }
    
    /**
     * Release the '/smartlifts' command from its ties to this class.
     */
    public void close() {
        plugin.getCommand("smartlifts").setExecutor(null);
    }
    
    /**
     * This method handles user commands. Usage: "/smartlifts"
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reload();
            sender.sendMessage("Configuration reloaded from disk.");
            return true;
        }
        
        return false;
    }
    
    /**
     * Handle tab-completion using subcommand List
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command,
            String alias, String[] args) {
        if (args.length == 1) {
            List<String> matches = new ArrayList<String>();
            for (String subcommand : subcommands) {
                if (StringUtil.startsWithIgnoreCase(subcommand, args[0])) {
                    matches.add(subcommand);
                }
            }
            if (!matches.isEmpty()) {
                return matches;
            }
        }
        return null;
    }
}
