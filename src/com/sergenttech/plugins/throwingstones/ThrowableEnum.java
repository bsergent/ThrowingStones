/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sergenttech.plugins.throwingstones;

import org.bukkit.potion.PotionEffect;

/**
 *
 * @author bserg_000
 */
public enum ThrowableEnum {
    
    COBBLESTONE(1.0f, 1, new PotionEffect[]{}, 0.8f, true);
    
    /*
    SPEEDMULTIPLIER
    1.2f -> ~30blks
    1.1f -> ~25blks
    1.0f -> ~20blks
    
    EXHAUSTION
    4.0f -> 1 hunger point
    0.8f -> sprint jumping
    */
    
    public final float speedMultiplier;
    public final int damage;
    public final PotionEffect[] effects;
    public final float exhaustion;
    public final boolean consumable;
 
    private ThrowableEnum(float speedMultiplier, int damage, PotionEffect[] effects, float exhaustion, boolean consumable) {
            this.speedMultiplier = speedMultiplier;
            this.damage = damage;
            this.effects = effects;
            this.exhaustion = exhaustion;
            this.consumable = consumable;
    }
    
}
