package io.github.robustum.teatopia.common

import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import pers.solid.brrp.v1.api.RuntimeResourcePack

object Teatopia : ModInitializer {

    const val MOD_ID: String = "teatopia"
    const val MOD_NAME: String = "Teatopia"

    internal val RESOURCE_PACK = RuntimeResourcePack.create(id("runtime"))

    val LOGGER: Logger = LogManager.getLogger(MOD_NAME)

    override fun onInitialize() {

    }

    @JvmStatic
    fun id(path: String) = Identifier(MOD_ID, path)

}