package com.example.paronychia.item;

import com.example.paronychia.registry.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ParonychiaCure extends Item {

    public ParonychiaCure(Settings settings) {
        super(settings.maxCount(64));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // 只有拥有甲沟炎效果时才允许使用
        if (user.hasStatusEffect(ModEffects.PARONYCHIA)) {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32; // 与药水一致，1.6 秒
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient && user instanceof PlayerEntity player) {
            if (player.hasStatusEffect(ModEffects.PARONYCHIA)) {
                player.removeStatusEffect(ModEffects.PARONYCHIA);
            }
            if (!player.isCreative()) {
                stack.decrement(1);
            }
            player.playSound(SoundEvents.ENTITY_PLAYER_BURP, 0.5F, 1.0F);
        }
        return stack;
    }
}