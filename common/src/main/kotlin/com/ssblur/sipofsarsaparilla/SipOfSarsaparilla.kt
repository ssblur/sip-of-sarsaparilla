package com.ssblur.sipofsarsaparilla

import com.ssblur.sipofsarsaparilla.block.SipBlocks
import com.ssblur.sipofsarsaparilla.item.SipItems
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
    }

    fun clientInit() {
        SipItems.registerClient()
        SipBlocks.registerClient()
    }
}