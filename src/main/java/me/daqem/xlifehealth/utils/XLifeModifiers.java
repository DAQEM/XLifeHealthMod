package me.daqem.xlifehealth.utils;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Collection;

public class XLifeModifiers {

    public static void applyMaxHealthModifier(PlayerEntity player, float amount) {
        IAttributeInstance attribute = player.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
        attribute.applyModifier(new AttributeModifier("MaxHealth", amount, AttributeModifier.Operation.ADDITION));
        player.setHealth(player.getMaxHealth());
    }

    public static void removeMaxHealthModifiers(PlayerEntity player) {
        IAttributeInstance iAttributeInstance = player.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
        Collection<AttributeModifier> modifiers = iAttributeInstance.getModifiers();
        for (AttributeModifier modifier : modifiers) {
            if (modifier.getName().equals("MaxHealth")) {
                iAttributeInstance.removeModifier(modifier);
            }
        }
    }

}
