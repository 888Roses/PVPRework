package net.rose.pvp_rework.api.util;

import net.minecraft.item.ToolMaterial;

public class ToolUtil {
    /**
     * The default attack speed of the player.
     */
    public static final float BASE_PLAYER_ATTACK_SPEED = 4.0F;

    public static float getEffectiveAttackSpeed(float attackSpeed) {
        return attackSpeed - BASE_PLAYER_ATTACK_SPEED;
    }

    public static int getAttackDamage(int damage, ToolMaterial material) {
        return Math.round(damage - 1 - material.getAttackDamage());
    }
}
