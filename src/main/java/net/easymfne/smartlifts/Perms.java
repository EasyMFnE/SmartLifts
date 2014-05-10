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

import org.bukkit.permissions.Permissible;

/**
 * This method provides a static way to check user permissions.
 * 
 * @author Eric Hildebrand
 */
public class Perms {
    
    /**
     * @param p
     *            User
     * @return Whether user can make Lifts
     */
    public static boolean canMakeLift(Permissible p) {
        return p.hasPermission("smartlifts.lift.make");
    }
    
    /**
     * @param p
     *            User
     * @return Whether user can use Lifts
     */
    public static boolean canUseLift(Permissible p) {
        return p.hasPermission("smartlifts.lift.use");
    }
    
}
