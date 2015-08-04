/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sergenttech.plugins.throwingstones;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
/**
 * 
 *
 * @author Ben Sergent V
 */
public class ThrowingStones extends JavaPlugin {
    
    private final String version = "0.1.0";
    private final HashMap<Player,Long> cooldowns = new HashMap();
    
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
                    if (e.getItemDrop().getItemStack().getType().name().equalsIgnoreCase(thr.name()) && e.getPlayer().getFoodLevel() >= getConfig().getInt("min_hunger", 0)) {
                        if (!cooldowns.containsKey(e.getPlayer()) || cooldowns.get(e.getPlayer())+getConfig().getLong("throw_cooldown_inMilli", 250)<System.currentTimeMillis()) {
                            ThrowableEnum throwable = ThrowableEnum.valueOf(e.getItemDrop().getItemStack().getType().name());
                            Vector vel = e.getPlayer().getLocation().getDirection();
                            vel = vel.multiply(throwable.speedMultiplier);
                            float randomMultiplier = 0.1f;
                            if (new Random().nextBoolean()) {
                                vel = vel.add(Vector.getRandom().multiply(randomMultiplier));
                            } else {
                                vel = vel.subtract(Vector.getRandom().multiply(randomMultiplier));
                            }
                            e.getItemDrop().setVelocity(vel);
                            e.getPlayer().setExhaustion(e.getPlayer().getExhaustion()+throwable.exhaustion);
                            e.getItemDrop().setPickupDelay((int) (ThrownRunnable.MAX_DURATION/1000*20));
                            new ThrownRunnable(e.getItemDrop(), throwable, e.getPlayer()).runTaskTimer(ThrowingStones.this, 0, 1);
                            cooldowns.put(e.getPlayer(), System.currentTimeMillis());
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.SHOOT_ARROW, 1.0f, 0.8f);
                        } else {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
    
}
