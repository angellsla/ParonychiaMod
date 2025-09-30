package com.example.paronychia.registry;

import com.example.paronychia.effect.ParonychiaEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final StatusEffect PARONYCHIA = new ParonychiaEffect(StatusEffectCategory.HARMFUL, 0xFF0000);

    public static void registerEffects() {
        Registry.register(Registries.STATUS_EFFECT, new Identifier("paronychia_mod", "paronychia"), PARONYCHIA);
    }
}