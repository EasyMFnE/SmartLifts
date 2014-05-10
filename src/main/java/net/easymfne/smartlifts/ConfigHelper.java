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

import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;

/**
 * Configuration helper class, with methods for accessing the configuration.
 */
public class ConfigHelper {
    
    private SmartLifts plugin = null;
    
    /**
     * Initialize the class and instantiate reference back to the plugin.
     * 
     * @param plugin
     *            The SmartLifts plugin
     * 
     * @author Eric Hildebrand
     */
    public ConfigHelper(SmartLifts plugin) {
        this.plugin = plugin;
    }
    
    /**
     * @return List of Material names that are impassable
     */
    public List<String> getBlockedMaterials() {
        return plugin.getConfig().getStringList("blocked");
    }
    
    /**
     * @return 'Fail' Sound
     */
    public Sound getFailSound() {
        try {
            return Sound.valueOf(plugin.getConfig().getString("sound.fail")
                    .toUpperCase());
        } catch (Exception e) {
            return Sound.NOTE_BASS;
        }
    }
    
    /**
     * @return Maximum vertical range of a Lift
     */
    public int getLiftRange() {
        return plugin.getConfig().getInt("range", 256);
    }
    
    /**
     * @return Error message when path is obstructed
     */
    public String getStringBlocked() {
        return plugin.getConfig().getString("strings.blocked");
    }
    
    /**
     * @return String for 'Down' Lifts
     */
    public String getStringDown() {
        return plugin.getConfig().getString("strings.down", "Down");
    }
    
    /**
     * @return Text on the Lift sign
     */
    public String getStringLiftText() {
        return plugin.getConfig().getString("strings.liftText", "[Lift]");
    }
    
    /**
     * @return Error message when lacking 'use' permission
     */
    public String getStringPermission() {
        return plugin.getConfig().getString("strings.permission");
    }
    
    /**
     * @return Error message when destination is out-of-range
     */
    public String getStringTooFar() {
        return plugin.getConfig().getString("strings.range");
    }
    
    /**
     * @return Error message when no destination found
     */
    public String getStringUnfound() {
        return plugin.getConfig().getString("strings.unfound");
    }
    
    /**
     * @return String for 'Up' Lifts
     */
    public String getStringUp() {
        return plugin.getConfig().getString("strings.up", "Up");
    }
    
    /**
     * @return 'Success' Sound
     */
    public Sound getUseSound() {
        try {
            return Sound.valueOf(plugin.getConfig().getString("sound.use")
                    .toUpperCase());
        } catch (Exception e) {
            return Sound.NOTE_PIANO;
        }
    }
    
    /**
     * @param material
     *            Material to check
     * @return Whether the material blocks Lifts
     */
    public boolean isBlocking(Material material) {
        for (String blocker : getBlockedMaterials()) {
            if (blocker.equalsIgnoreCase(material.name())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @return Whether sounds are played
     */
    public boolean isSoundPlayed() {
        return plugin.getConfig().getBoolean("sound.enabled", false);
    }
    
}
