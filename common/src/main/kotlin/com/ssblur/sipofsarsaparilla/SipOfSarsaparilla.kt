package com.ssblur.sipofsarsaparilla

import com.ssblur.unfocused.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object SipOfSarsaparilla : ModInitializer("sip_of_sarsaparilla") {
    const val MODID = "sip_of_sarsaparilla"
    val LOGGER: Logger = LoggerFactory.getLogger(id)

    fun init() {
        LOGGER.info("Sip Of Sarsaparilla loaded...")
    }

    fun clientInit() {
        LOGGER.info("Sip Of Sarsaparilla loaded...")
    }
}