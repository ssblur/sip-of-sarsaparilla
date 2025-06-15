package com.ssblur.sipofsarsaparilla.trades

import com.ssblur.sipofsarsaparilla.SipOfSarsaparilla
import com.ssblur.sipofsarsaparilla.item.SipItems
import com.ssblur.unfocused.entity.Trades.registerVillagerTrade
import net.minecraft.world.entity.npc.VillagerProfession
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.trading.ItemCost
import net.minecraft.world.item.trading.MerchantOffer
import java.util.*

object SipTrades {
  fun register() {
    SipOfSarsaparilla.registerVillagerTrade(VillagerProfession.LEATHERWORKER, 2) { entity, random ->
      MerchantOffer(
        ItemCost(SipItems.COWBOY_HAT.get()),
        Optional.of(ItemCost(Items.EMERALD, 3 + random.nextInt(1))),
        ItemStack(SipItems.THE_DIMMADOME),
        1,
        2,
        9.0f
      )
    }
  }
}