package me.daqem.xlifehealth.utils;

import net.minecraft.entity.player.PlayerEntity;

public class SecurityCheck {

    public static void securityCheck(PlayerEntity player) {
        if (!(player.getPersistentData().getBoolean("xlifehealth.firstJoin"))) {
            player.getPersistentData().putBoolean("xlifehealth.firstJoin", true);
        }
    }
}
