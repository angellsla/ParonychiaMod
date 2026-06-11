package com.example.paronychia.event;

import com.example.paronychia.registry.ModEffects;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class ParonychiaEvent {

    private static final Random RAND = new Random();
    // 每个玩家独立的落地前 fallDistance 记录，避免共享状态
    private static final Map<UUID, Float> LAST_FALL_MAP = new HashMap<>();

    public static void register() {
        // 玩家落地事件：从高处摔下有几率获得甲沟炎
        ServerPlayerEvents.ALLOW_DEATH.register((player, damageSource, damageAmount) -> {
            // 仅用于拦截死亡，不做额外扣血；保持原有逻辑返回 true
            return true;
        });

        // 使用 END_SERVER_TICK 做跳跃/撞墙额外伤害和落地检测
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
    private static void onLand(ServerPlayerEntity player) {
        UUID uuid = player.getUuid();
        if (player.isOnGround()) {
            float fall = player.fallDistance;
            float lastFall = LAST_FALL_MAP.getOrDefault(uuid, 0.0F);
            if (fall >= 3.0F && lastFall != fall) {
                if (RAND.nextFloat() < 0.30F) {
                    player.addStatusEffect(
                            new StatusEffectInstance(ModEffects.PARONYCHIA,
                                    20 * 60 * 3,   // 3 min
                                    0, false, true, true));
                }
            }
            LAST_FALL_MAP.put(uuid, fall);
        } else {
            LAST_FALL_MAP.put(uuid, 0.0F);
        }
    }

    /* ===== 跳跃额外伤害 ===== */
    private static void doJumpDamage(ServerPlayerEntity player) {
        // 上升阶段且每 20 tick（1 秒）触发一次
        if (player.getVelocity().y > 0 && player.age % 20 == 0) {
            hurtByLevel(player);
        }
    }

    /* ===== 撞墙额外伤害 ===== */
    private static void doWallDamage(ServerPlayerEntity player) {
        // 水平碰撞且正在下落时触发
        if (player.horizontalCollision && player.getVelocity().y < 0) {
            hurtByLevel(player);
        }
    }

    /* ===== 按等级造成额外伤害 ===== */
    private static void hurtByLevel(ServerPlayerEntity player) {
        StatusEffectInstance effect = player.getStatusEffect(ModEffects.PARONYCHIA);
        if (effect == null) return;

        int amplifier = effect.getAmplifier();
        float damage = switch (amplifier) {
            case 0 -> 2.0F;   // Ⅰ级 2 点
            case 1 -> 4.0F;   // Ⅱ级 4 点
            default -> 6.0F;  // Ⅲ级及以上 6 点
        };

        player.damage(player.getWorld().getDamageSources().generic(), damage);
    }
}