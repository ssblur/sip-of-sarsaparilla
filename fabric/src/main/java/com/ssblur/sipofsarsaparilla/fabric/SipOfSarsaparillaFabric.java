package com.ssblur.sipofsarsaparilla.fabric;

import com.ssblur.sipofsarsaparilla.SipOfSarsaparilla;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class SipOfSarsaparillaFabric implements ModInitializer, ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SipOfSarsaparilla.INSTANCE.clientInit();
    }

    @Override
    public void onInitialize() {
        SipOfSarsaparilla.INSTANCE.init();
    }
}
