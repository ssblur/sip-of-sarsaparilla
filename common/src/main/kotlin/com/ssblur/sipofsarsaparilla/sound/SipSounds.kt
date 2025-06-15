package com.ssblur.sipofsarsaparilla.sound

import com.ssblur.sipofsarsaparilla.SipOfSarsaparilla
import net.minecraft.sounds.SoundEvent

object SipSounds {
  val NO_AMMO_CLICK = SipOfSarsaparilla.registerSound("no_ammo_click") {
    SoundEvent.createVariableRangeEvent(SipOfSarsaparilla.location("no_ammo_click"))
  }
  val GUN_CAP_POP = SipOfSarsaparilla.registerSound("gun_cap_pop") {
    SoundEvent.createVariableRangeEvent(SipOfSarsaparilla.location("gun_cap_pop"))
  }
  val GUN_BLAST = SipOfSarsaparilla.registerSound("gun_blast") {
    SoundEvent.createVariableRangeEvent(SipOfSarsaparilla.location("gun_blast"))
  }
  val GUN_RELOAD = SipOfSarsaparilla.registerSound("gun_reload") {
    SoundEvent.createVariableRangeEvent(SipOfSarsaparilla.location("gun_reload"))
  }

  fun register() {}

  fun registerClient() {}
}