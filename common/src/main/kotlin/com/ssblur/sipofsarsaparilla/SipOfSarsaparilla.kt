package com.ssblur.sipofsarsaparilla

import com.ssblur.sipofsarsaparilla.block.SipBlocks
import com.ssblur.sipofsarsaparilla.feature.SipFeatures
import com.ssblur.sipofsarsaparilla.item.SipItems
import com.ssblur.sipofsarsaparilla.network.client.SipNetworkS2C
import com.ssblur.sipofsarsaparilla.network.server.SipNetworkC2S
import com.ssblur.sipofsarsaparilla.sound.SipSounds
import com.ssblur.unfocused.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
object SipOfSarsaparilla : ModInitializer("sipofsarsaparilla") {
    const val MODID = "sipofsarsaparilla"
    val LOGGER: Logger = LoggerFactory.getLogger(id)

    fun init() {
        SipItems.register()
        SipBlocks.register()
        SipFeatures.register()
        SipSounds.register()
        SipNetworkC2S.register()
        SipNetworkS2C.register()
    }

    fun clientInit() {
        SipItems.registerClient()
        SipBlocks.registerClient()
        SipSounds.registerClient()
    }
}