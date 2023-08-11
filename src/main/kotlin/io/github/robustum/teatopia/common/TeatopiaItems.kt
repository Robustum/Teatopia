package io.github.robustum.teatopia.common

import net.devtech.arrp.json.models.JModel
import net.devtech.arrp.json.models.JTextures
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

object TeatopiaItems {

    private fun addSimpleItem(name: String, localizationEn: String, localizationJp: String = localizationEn): Item =
        addItem(Item(Item.Settings().group(Teatopia.itemGroup)), name, localizationEn, localizationJp)

    internal fun addBlockItem(
        block: Block,
        name: String,
        localizationEn: String,
        localizationJp: String = localizationEn
    ): Item =
        addItem(
            BlockItem(block, Item.Settings().group(Teatopia.itemGroup)),
            name,
            localizationEn,
            localizationJp,
            id("block/$name").toString()
        )

    private fun addItem(
        item: Item,
        name: String,
        localizationEn: String,
        localizationJp: String = localizationEn,
        modelParent: String = "item/generated"
    ): Item {
        val registered = Registry.register(Registry.ITEM, id(name), item)
        Teatopia.localeEn_Us.item(id(name), localizationEn)
        Teatopia.localeJa_Jp.item(id(name), localizationJp)
        Teatopia.RESOURCE_PACK.addModel(
            JModel.model(modelParent).textures(JTextures().layer0("${Teatopia.MOD_ID}:item/$name")),
            id("item/$name")
        )
        return registered
    }
}