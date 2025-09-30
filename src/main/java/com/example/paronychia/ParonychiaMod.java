package com.example.paronychia;

import com.example.paronychia.event.ParonychiaEvent;
import com.example.paronychia.registry.ModEffects;
import com.example.paronychia.registry.ModItems;
import net.fabricmc.api.ModInitializer;

public class ParonychiaMod implements ModInitializer {
    @Override
    public void onInitialize() {
        ModEffects.registerEffects();
        ModItems.registerItems();
        ParonychiaEvent.register();   // 关键事件注册
        System.out.println("甲沟炎 Mod 已加载！");
    }
}