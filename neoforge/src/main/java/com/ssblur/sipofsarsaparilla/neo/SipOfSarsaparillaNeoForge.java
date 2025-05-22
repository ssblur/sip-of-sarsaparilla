package com.ssblur.sipofsarsaparilla.neo;

import com.ssblur.sipofsarsaparilla.SipOfSarsaparilla;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(SipOfSarsaparilla.MODID)
public final class SipOfSarsaparillaNeoForge {
    public SipOfSarsaparillaNeoForge() {
        SipOfSarsaparilla.INSTANCE.init();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            SipOfSarsaparilla.INSTANCE.clientInit();
        }
    }
}
