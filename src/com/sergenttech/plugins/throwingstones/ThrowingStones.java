/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sergenttech.plugins.throwingstones;

import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
/**
 * 
 *
 * @author Ben Sergent V
 */
public class ThrowingStones extends JavaPlugin {
    
    private final String version = "0.0.3";
    
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
            if (e.getPlayer().isSneaking()) {
                for (ThrowableEnum thr : ThrowableEnum.values()) {
                    if (e.getItemDrop().getItemStack().getType().name().equalsIgnoreCase(thr.name())) {
                        ThrowableEnum throwable = ThrowableEnum.valueOf(e.getItemDrop().getItemStack().getType().name());
                        Vector vel = e.getPlayer().getLocation().getDirection();
                        vel = vel.multiply(throwable.speedMultiplier);
                        vel = vel.add(Vector.getRandom().multiply(0.2f));
                        e.getItemDrop().setVelocity(vel);
                        //e.getItemDrop().setVelocity(e.getItemDrop().getVelocity().multiply(throwable.speedMultiplier));
                        // TODO Make throws slightly less random by using player head directiona and then randomizing
                        e.getPlayer().setExhaustion(e.getPlayer().getExhaustion()+throwable.exhaustion);
                        e.getItemDrop().setPickupDelay(100);
                        // e.getItemDrop().setMetadata("throwable", null);
                        // TODO Use .setMetaData() to track? or an invisible arrow?
                        // TODO Set PickupDelay back to 0 when item stops moving
                        // TODO Stop item from moving when hitting a player or entity or use .isOnGround()
                        //e.getPlayer().sendMessage("You threw some "+e.getItemDrop().getName()+" at "+e.getItemDrop().getVelocity()+"!");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.SHOOT_ARROW, 1.0f, 2.0f);
                    }
                }
            }
        }
    }
    
}
