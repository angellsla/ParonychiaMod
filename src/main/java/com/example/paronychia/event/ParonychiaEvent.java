package com.example.paronychia.event;

import com.example.paronychia.registry.ModEffects;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;

import java.util.Random;

public class ParonychiaEvent {

    private static final Random RAND = new Random();

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (player.hasStatusEffect(ModEffects.PARONYCHIA)) {
                    doJumpDamage(player);
                    doWallDamage(player);
                }
                onLand(player);
            }
        });
    }

    /* ===== 落地给 Buff ===== */
    private static float lastFall = 0;

    private static void onLand(ServerPlayerEntity player) {
        if (player.isOnGround()) {
            float fall = player.fallDistance;
            if (fall >= 3.0F && lastFall != fall) {
                if (RAND.nextFloat() < 0.30F) {
                    player.addStatusEffect(
                            new StatusEffectInstance(ModEffects.PARONYCHIA,
                                    20 * 60 * 3,   // 3 min
                                    0, false, true, true));
                }
            }
            lastFall = fall;
        } else {
            lastFall = 0;
        }
    }

    /* ===== 跳跃伤害 ===== */
    private static void doJumpDamage(PlayerEntity player) {
        if (player.getVelocity().y > 0 && player.age % 20 == 0) {
            hurtByLevel(player);
        }
    }

    /* ===== 撞墙伤害 ===== */
    private static void doWallDamage(PlayerEntity player) {
        if (player.horizontalCollision && player.getVelocity().y < 0) {
            hurtByLevel(player);
        }
    }

    /* ===== 按等级掉血 ===== */
    private static void hurtByLevel(PlayerEntity player) {
        StatusEffectInstance effect = player.getStatusEffect(ModEffects.PARONYCHIA);
        if (effect == null) return;

        int amplifier = effect.getAmplifier();
        float damage = switch (amplifier) {
            case 0 -> 3.0F;   // Ⅰ级 3 点
            case 1 -> 5.0F;   // Ⅱ级 5 点
            default -> 8.0F;  // Ⅲ级及以上 8 点
        };

        player.damage(player.getWorld().getDamageSources().generic(), damage);
        player.playSound(SoundEvents.ENTITY_PLAYER_HURT, 0.5F, 1.0F);
    }
}