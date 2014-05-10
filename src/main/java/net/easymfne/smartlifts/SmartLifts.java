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

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

/**
 * Main plugin class, responsible for its own setup, logging, reloading, and
 * shutdown operations. Maintains instances of ConfigHelper, SmartLiftsCommand,
 * and PlayerListener.
 * 
 * @author Eric Hildebrand
 */
public class SmartLifts extends JavaPlugin {
    
    private ConfigHelper configHelper = null;
    private SmartLiftsCommand smartLiftsCommand = null;
    private PlayerListener playerListener = null;
    
    /* Strings for fancyLog() methods */
    private final String logPrefix = ChatColor.BLUE + "[SmartLifts] ";
    private final String logColor = ChatColor.AQUA.toString();
    
    /**
     * Log a message to the console using color, with a specific logging Level.
     * If there is no console open, log the message without any coloration.
     * 
     * @param level
     *            Level at which the message should be logged
     * @param message
     *            The message to be logged
     */
    protected void fancyLog(Level level, String message) {
        if (getServer().getConsoleSender() != null) {
            getServer().getConsoleSender().sendMessage(
                    logPrefix + logColor + message);
        } else {
            getServer().getLogger().log(level,
                    ChatColor.stripColor(logPrefix + message));
        }
    }
    
    /**
     * Log a message to the console using color, defaulting to the Info level.
     * If there is no console open, log the message without any coloration.
     * 
     * @param message
     *            The message to be logged
     */
    protected void fancyLog(String message) {
        fancyLog(Level.INFO, message);
    }
    
    /**
     * @return the configuration helper instance
     */
    public ConfigHelper getConfigHelper() {
        return configHelper;
    }
    
    /**
     * Close all event handlers and command listeners, then null instances to
     * mark them for garbage collection. Displays elapsed time to console when
     * finished.
     */
    @Override
    public void onDisable() {
        long start = Calendar.getInstance().getTimeInMillis();
        fancyLog("=== DISABLE START ===");
        playerListener.close();
        playerListener = null;
        smartLiftsCommand.close();
        smartLiftsCommand = null;
        configHelper = null;
        fancyLog("=== DISABLE COMPLETE ("
                + (Calendar.getInstance().getTimeInMillis() - start)
                + "ms) ===");
    }
    
    /**
     * Set up the plugin by: loading config.yml (creating from default if not
     * existent), then instantiating its own ConfigHelper, SmartLiftsCommand,
     * and PlayerListener objects. Displays elapsed time to console when
     * finished.
     */
    @Override
    public void onEnable() {
        long start = Calendar.getInstance().getTimeInMillis();
        fancyLog("=== ENABLE START ===");
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveDefaultConfig();
            fancyLog("Saved default config.yml");
        }
        
        configHelper = new ConfigHelper(this);
        smartLiftsCommand = new SmartLiftsCommand(this);
        playerListener = new PlayerListener(this);
        startMetrics();
        fancyLog("=== ENABLE COMPLETE ("
                + (Calendar.getInstance().getTimeInMillis() - start)
                + "ms) ===");
    }
    
    /**
     * Reload the configuration from disk and perform any necessary functions.
     * Displays elapsed time to console when finished.
     */
    public void reload() {
        long start = Calendar.getInstance().getTimeInMillis();
        fancyLog("=== RELOAD START ===");
        reloadConfig();
        fancyLog("=== RELOAD COMPLETE ("
                + (Calendar.getInstance().getTimeInMillis() - start)
                + "ms) ===");
    }
    
    /**
     * If possible, instantiate Metrics and connect with mcstats.org
     */
    private void startMetrics() {
        MetricsLite metrics;
        try {
            metrics = new MetricsLite(this);
            if (metrics.start()) {
                fancyLog("Metrics enabled.");
            }
        } catch (IOException e) {
            fancyLog(Level.WARNING, "Metrics exception: " + e.getMessage());
        }
    }
    
}
