package com.ssblur.sipofsarsaparilla.network.client

import com.ssblur.sipofsarsaparilla.SipOfSarsaparilla.location
import com.ssblur.sipofsarsaparilla.network.server.SipNetworkC2S
import com.ssblur.unfocused.network.NetworkManager
import net.minecraft.client.Minecraft
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.projectile.ProjectileUtil
import net.minecraft.world.phys.AABB

object SipNetworkS2C {
  val GUN_RANGE = 20.0
  data class RequestGunTrace(val hand: InteractionHand?)
  val requestGunTrace = NetworkManager.registerS2C(
    location("client_gun_trace"),
    RequestGunTrace::class
  ) { (hand) ->
    val player = Minecraft.getInstance().player
    val level = player!!.level()
    val pos = player.eyePosition
    val angle = player.lookAngle.normalize().multiply(GUN_RANGE, GUN_RANGE, GUN_RANGE)
    val dest = angle.add(pos)

    val hitResult = ProjectileUtil.getEntityHitResult(
      level,
      player,
      pos,
      dest,
      AABB.ofSize(pos.subtract(0.1, 0.1, 0.1), 0.2, 0.2, 0.2).expandTowards(angle).inflate(1.0)
    ) { _ -> true }

    SipNetworkC2S.gunTrace(SipNetworkC2S.GunTrace(hitResult?.entity?.id, hand))
  }

  fun register() {}
}