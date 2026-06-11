package com.example.paronychia.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.sound.SoundEvents;

import java.util.UUID;

public class ParonychiaEffect extends StatusEffect {

    private static final UUID SPEED_MODIFIER_UUID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");

    public ParonychiaEffect(StatusEffectCategory category, int color) {
        super(category, color);
        // 添加移动速度降低属性修改器（Ⅰ级 -10%，每级额外 -10%）
        this.addAttributeModifier(
                EntityAttributes.GENERIC_MOVEMENT_SPEED,
                SPEED_MODIFIER_UUID.toString(),
                -0.10,
                EntityAttributeModifier.Operation.MULTIPLY_TOTAL
        );
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        // 每 40 tick（2 秒）造成一次持续伤害
        float damage = switch (amplifier) {
            case 0 -> 1.0F;   // Ⅰ级
            case 1 -> 2.0F;   // Ⅱ级
            default -> 3.0F;  // Ⅲ级及以上
        };
        entity.damage(entity.getWorld().getDamageSources().generic(), damage);
        entity.playSound(SoundEvents.ENTITY_PLAYER_HURT, 0.4F, 1.0F);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // 每 40 tick 触发一次 applyUpdateEffect
        return duration % 40 == 0;
    }
}