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
    
    COBBLESTONE(3f, 1, new PotionEffect[]{}, 0.8f);
    
    public final float speedMultiplier;
    public final int damage;
    public final PotionEffect[] effects;
    public final float exhaustion; // 4.0 is a hunger point
 
    private ThrowableEnum(float speedMultiplier, int damage, PotionEffect[] effects, float exhaustion) {
            this.speedMultiplier = speedMultiplier;
            this.damage = damage;
            this.effects = effects;
            this.exhaustion = exhaustion;
    }
    
}
