/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sergenttech.plugins.throwingstones;

import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;
/**
 * 
 *
 * @author Ben Sergent V
 */
public class ThrowingStones extends JavaPlugin {
    
    private final String version = "0.0.1";
    
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        
        try {
            org.mcstats.MetricsLite metrics = new org.mcstats.MetricsLite(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }
        
        getServer().getPluginManager().registerEvents(new ThrowListener(), this);
        
        getLogger().log(Level.INFO, "ThrowingStones v{0} enabled.", version);
    }
    
    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "ThrowingStones v{0} disabled.", version);
    }
    
    public final class ThrowListener implements org.bukkit.event.Listener {
        @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL)
        public void onPlayerThrow(org.bukkit.event.player.PlayerDropItemEvent e) {
            
        }
    }
    
}
