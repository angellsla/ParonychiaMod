package com.example.paronychia.event;

import com.example.paronychia.registry.ModEffects;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class PlayerEventHandler {
    public static void registerEvents() {
        ServerPlayerEvents.ALLOW_DEATH.register((player, damageSource, damageAmount) -> {
            if (player.hasStatusEffect(ModEffects.PARONYCHIA)) {
                // Example logic for reducing health on jump or block collision
                StatusEffectInstance effect = player.getStatusEffect(ModEffects.PARONYCHIA);
                if (effect != null) {
                    int level = effect.getAmplifier() + 1;
                    player.damage(damageSource, level * 2); // Deduct health based on level
                }
            }
            return true;
        });
    }
}