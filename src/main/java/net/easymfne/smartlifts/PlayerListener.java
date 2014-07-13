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

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

/**
 * The class that monitors and reacts to server events.
 * 
 * @author Eric Hildebrand
 */
public class PlayerListener implements Listener {
    
    private SmartLifts plugin = null;
    private String pass = ChatColor.DARK_BLUE.toString();
    private String fail = ChatColor.DARK_RED.toString();
    
    /**
     * Initialize by instantiating reference to the plugin and registering each
     * of the defined EventHandlers.
     * 
     * @param plugin
     *            Reference to SmartLifts plugin instance
     */
    public PlayerListener(SmartLifts plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    /**
     * Unregister all registered EventHandlers, preventing further reactions.
     */
    public void close() {
        HandlerList.unregisterAll(this);
    }
    
    /**
     * Prevent placing of blocks when players are clicking Lifts
     * 
     * @param event
     *            The block-place event
     */
    @EventHandler(ignoreCancelled = true)
    public void OnBlockPlace(BlockPlaceEvent event) {
        if (event.getBlockAgainst().getState() instanceof Sign) {
            Sign sign = (Sign) event.getBlockAgainst().getState();
            if (sign.getLine(0).equals(
                    pass + plugin.getConfigHelper().getStringLiftText())) {
                event.setCancelled(true);
            }
        }
    }
    
    /**
     * Detect players attempting to use Lifts
     * 
     * @param event
     *            The player's click event
     */
    @EventHandler(ignoreCancelled = false)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || !event.hasBlock()
                || !(event.getClickedBlock().getState() instanceof Sign)) {
            return;
        }
        Sign sign = (Sign) event.getClickedBlock().getState();
        if (sign.getLine(0).equals(
                pass + plugin.getConfigHelper().getStringLiftText())) {
            event.setCancelled(true);
            if (!Perms.canUseLift(event.getPlayer())) {
                return;
            }
            if (sign.getLine(1).equals(plugin.getConfigHelper().getStringUp())) {
                useLift(event.getPlayer(), sign, 1);
            } else if (sign.getLine(1).equals(
                    plugin.getConfigHelper().getStringDown())) {
                useLift(event.getPlayer(), sign, -1);
            }
        }
    }
    
    /**
     * Detect placing of signs to convert them to Lifts
     * 
     * @param event
     *            Sign placing event
     */
    @EventHandler(ignoreCancelled = true)
    public void onSignChange(SignChangeEvent event) {
        String[] lines = event.getLines().clone();
        /* Prevent trickery with color codes faking Lift signs */
        lines[0] = ChatColor.stripColor(ChatColor.translateAlternateColorCodes(
                '&', lines[0]));
        if (lines[0].equals(plugin.getConfigHelper().getStringLiftText())) {
            if (Perms.canMakeLift(event.getPlayer())) {
                if (lines[1].equals(plugin.getConfigHelper().getStringUp())
                        || lines[1].equals(plugin.getConfigHelper()
                                .getStringDown()) || lines[1].equals("")) {
                    event.setLine(0, pass + lines[0]);
                } else {
                    event.setLine(0, fail + lines[0]);
                }
            } else {
                sendError(event.getPlayer(), plugin.getConfigHelper()
                        .getStringPermission());
                event.setLine(0, event.getLine(0));
            }
        }
    }
    
    /**
     * Play Lift sound, based on success.
     * 
     * @param location
     *            Location of sound
     * @param success
     *            Success of Lift
     */
    private void playSound(Location location, boolean success) {
        if (success) {
            location.getWorld().playSound(location,
                    plugin.getConfigHelper().getUseSound(), 1f, 1f);
        } else {
            location.getWorld().playSound(location,
                    plugin.getConfigHelper().getFailSound(), 1f, 1f);
        }
    }
    
    /**
     * Standardized error message formatting.
     * 
     * @param sender
     *            Recipient
     * @param error
     *            Message
     */
    private void sendError(CommandSender sender, String error) {
        sender.sendMessage(ChatColor.RED + error);
    }
    
    /**
     * @param player
     * @param sign
     */
    private void useLift(Player player, Sign lift, int direction) {
        Location destination = null;
        boolean blocked = false;
        int count = 0;
        for (int y = lift.getY() + direction; y <= 255 && y >= 1; y += direction) {
            count++;
            Block block = player.getWorld().getBlockAt(lift.getX(), y,
                    lift.getZ());
            if (block.getState() instanceof Sign) {
                Sign sign = (Sign) block.getState();
                if (sign.getLine(0).equals(
                        pass + plugin.getConfigHelper().getStringLiftText())) {
                    destination = sign.getLocation();
                    if (player.getWorld()
                            .getBlockAt(destination.clone().subtract(0, 1, 0))
                            .getType().equals(Material.AIR)) {
                        destination.subtract(0, 1, 0);
                    }
                    destination.add(0.5, 0, 0.5);
                    destination.setYaw(player.getLocation().getYaw());
                    destination.setPitch(player.getLocation().getPitch());
                    break;
                }
            } else if (plugin.getConfigHelper().isBlocking(block.getType())) {
                blocked = true;
            }
        }
        if (destination == null) {
            sendError(player, plugin.getConfigHelper().getStringUnfound());
        } else if (blocked) {
            sendError(player, plugin.getConfigHelper().getStringBlocked());
        } else if (count > plugin.getConfigHelper().getLiftRange()) {
            sendError(player, plugin.getConfigHelper().getStringTooFar());
        } else {
            player.teleport(destination, TeleportCause.PLUGIN);
            playSound(destination, true);
            return;
        }
        playSound(player.getLocation(), false);
    }
}
