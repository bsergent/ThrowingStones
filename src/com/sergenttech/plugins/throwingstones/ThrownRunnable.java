/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sergenttech.plugins.throwingstones;

import java.util.List;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

/**
 *
 * @author bserg_000
 */
public class ThrownRunnable extends org.bukkit.scheduler.BukkitRunnable {
    
    public static final long MAX_DURATION = 5000;
    
    private final long endTime;
    private Item item;
    private ThrowableEnum type;
    private Player source;

    public ThrownRunnable(Item item, ThrowableEnum type, Player source) {
        this.item = item;
        this.type = type;
        this.source = source;
        this.endTime = System.currentTimeMillis() + MAX_DURATION;
    }

    @Override
    public void run() {
        if (System.currentTimeMillis() > endTime) {
            this.cancel();
            return;
        }
        if (item.isOnGround() && item.getVelocity().equals(new Vector())) {
            item.setPickupDelay(0);
            item.setVelocity(new Vector(0,0,0));
            this.cancel();
            return;
        }
        List<Entity> entities = item.getNearbyEntities(0.2, 0.2, 0.2);
        for (Entity e : entities) {
            if (e != source && e instanceof org.bukkit.entity.Damageable) {
                org.bukkit.entity.Damageable ed = (org.bukkit.entity.Damageable) e;
                ed.damage(type.damage, source);
                if (e instanceof org.bukkit.entity.LivingEntity) {
                    org.bukkit.entity.LivingEntity el = (org.bukkit.entity.LivingEntity) e;
                    for (PotionEffect effect : type.effects) {
                        el.addPotionEffect(effect);
                    }
                }
                item.remove();
                source.playSound(source.getLocation(), Sound.ARROW_HIT, 1.0f, 0.8f);
                this.cancel();
            }
        }
    }
    
}
