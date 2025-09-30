package com.example.paronychia.item;

import com.example.paronychia.registry.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ParonychiaCure extends Item {

    public ParonychiaCure(Settings settings) {
        super(settings.maxCount(64));   // 不写分组
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 60; // 3 s
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingTicks) {
        if (!(user instanceof PlayerEntity player)) return;

        int usedTicks = getMaxUseTime(stack) - remainingTicks;
        if (usedTicks >= 60) {
            if (!world.isClient) {
                if (player.hasStatusEffect(ModEffects.PARONYCHIA)) {
                    player.removeStatusEffect(ModEffects.PARONYCHIA);
                }
                if (!player.isCreative()) {
                    stack.decrement(1);
                }
                player.playSound(SoundEvents.ENTITY_PLAYER_BURP, 0.5F, 1.0F);
            }
        }
    }
}