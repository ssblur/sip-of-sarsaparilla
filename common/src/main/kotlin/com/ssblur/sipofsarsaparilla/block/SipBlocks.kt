package com.ssblur.sipofsarsaparilla.block

import com.ssblur.sipofsarsaparilla.SipOfSarsaparilla.registerBlockWithItem
import com.ssblur.sipofsarsaparilla.item.SipItems
import com.ssblur.unfocused.tab.CreativeTabs.tab
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy

object SipBlocks {
  val OAK_SALOON_DOOR = registerBlockWithItem("oak_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.ACACIA_DOOR)) }
  val SPRUCE_SALOON_DOOR = registerBlockWithItem("spruce_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.ACACIA_DOOR)) }
  val BIRCH_SALOON_DOOR = registerBlockWithItem("birch_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.ACACIA_DOOR)) }
  val JUNGLE_SALOON_DOOR = registerBlockWithItem("jungle_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.ACACIA_DOOR)) }
  val ACACIA_SALOON_DOOR = registerBlockWithItem("acacia_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.ACACIA_DOOR)) }
  val DARK_OAK_SALOON_DOOR = registerBlockWithItem("dark_oak_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.ACACIA_DOOR)) }
  val MANGROVE_SALOON_DOOR = registerBlockWithItem("mangrove_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.ACACIA_DOOR)) }
  val CHERRY_SALOON_DOOR = registerBlockWithItem("cherry_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.ACACIA_DOOR)) }
  val CRIMSON_SALOON_DOOR = registerBlockWithItem("crimson_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.ACACIA_DOOR)) }
  val WARPED_SALOON_DOOR = registerBlockWithItem("warped_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.ACACIA_DOOR)) }
  val BAMBOO_SALOON_DOOR = registerBlockWithItem("bamboo_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.ACACIA_DOOR)) }
  val PALE_SALOON_DOOR = registerBlockWithItem("pale_saloon_door"){ SaloonDoor(ofFullCopy(Blocks.ACACIA_DOOR)) }

  fun register() {
    OAK_SALOON_DOOR.second.tab(SipItems.TAB)
    SPRUCE_SALOON_DOOR.second.tab(SipItems.TAB)
    BIRCH_SALOON_DOOR.second.tab(SipItems.TAB)
    JUNGLE_SALOON_DOOR.second.tab(SipItems.TAB)
    ACACIA_SALOON_DOOR.second.tab(SipItems.TAB)
    DARK_OAK_SALOON_DOOR.second.tab(SipItems.TAB)
    MANGROVE_SALOON_DOOR.second.tab(SipItems.TAB)
    CHERRY_SALOON_DOOR.second.tab(SipItems.TAB)
    CRIMSON_SALOON_DOOR.second.tab(SipItems.TAB)
    WARPED_SALOON_DOOR.second.tab(SipItems.TAB)
    BAMBOO_SALOON_DOOR.second.tab(SipItems.TAB)
    PALE_SALOON_DOOR.second.tab(SipItems.TAB)
  }

  fun registerClient() {

  }
}