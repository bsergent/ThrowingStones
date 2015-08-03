/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sergenttech.plugins.throwingstones;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

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
        if (item.isOnGround()) {
            source.sendMessage("The item hit the ground.");
            this.cancel();
        }
    }
    
}
