package io.github.robustum.teatopia.common

import net.devtech.arrp.api.RRPCallback
import net.devtech.arrp.api.RuntimeResourcePack
import net.devtech.arrp.json.lang.JLang
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object Teatopia : ModInitializer {

    const val MOD_ID: String = "teatopia"
    const val MOD_NAME: String = "Teatopia"

    internal val RESOURCE_PACK = RuntimeResourcePack.create(id("runtime"))
    internal val localeJa_Jp = JLang.lang()
    internal val localeEn_Us = JLang.lang()

    val itemGroup = FabricItemGroupBuilder.build(id("item_group")) { ItemStack(Items.APPLE) } // ToDo: お茶に変える1

    val logger: Logger = LogManager.getLogger(MOD_NAME)

    override fun onInitialize() {
        RESOURCE_PACK.addLang(id("ja_jp"), localeJa_Jp)
        RESOURCE_PACK.addLang(id("en_us"), localeEn_Us)
        RRPCallback.BEFORE_VANILLA.register { it.add(RESOURCE_PACK) }
    }
}

internal fun id(path: String) = Identifier(Teatopia.MOD_ID, path)
