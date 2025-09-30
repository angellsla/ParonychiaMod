package com.example.paronychia.registry;

import com.example.paronychia.item.ParonychiaCure;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item PARONYCHIA_CURE = new ParonychiaCure(new Item.Settings());

    public static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier("paronychia_mod", "paronychia_cure"), PARONYCHIA_CURE);
    }
}