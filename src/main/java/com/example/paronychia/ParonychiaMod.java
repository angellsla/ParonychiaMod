package com.example.paronychia;

import com.example.paronychia.event.ParonychiaEvent;
import com.example.paronychia.registry.ModEffects;
import com.example.paronychia.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParonychiaMod implements ModInitializer {

    public static final String MOD_ID = "paronychia_mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModEffects.registerEffects();
        ModItems.registerItems();
        ParonychiaEvent.register();
        LOGGER.info("甲沟炎 Mod 已加载！");
    }
}