package io.github.robustum.teatopia.common

import net.devtech.arrp.json.models.JModel
import net.devtech.arrp.json.models.JTextures
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

object TeatopiaItems {

    val freshTeaLeaf by lazy {
        addSimpleItem("fresh_tea_leaf", "Fresh Tea Leaf")
    }

    val teaSapling by lazy {
        addSimpleItem("tea_sapling", "Tea Sapling")
    }

    val driedTeaLeaves by lazy {
        addSimpleItem("dried_tea_leaves", "Dried tea leaves ")
    }

    val usedTeaLeaves by lazy {
        addSimpleItem("used_tea_leaves", "Used tea leaves")
    }

    val lightShieldingSheet by lazy {
        addSimpleItem("light_shielding_sheet", "Light shielding sheet")
    }


    private fun addSimpleItem(name: String, localizationEn: String): Item =
        addItem(Item(Item.Settings().group(Teatopia.itemGroup)), name, localizationEn)

    internal fun addBlockItem(
        block: Block,
        name: String,
        localizationEn: String,
    ): Item =
        addItem(
            BlockItem(block, Item.Settings().group(Teatopia.itemGroup)),
            name,
            localizationEn,
            id("block/$name").toString()
        )

    private fun addItem(
        item: Item,
        name: String,
        localizationEn: String,
        modelParent: String = "item/generated"
    ): Item {
        val registered = Registry.register(Registry.ITEM, id(name), item)
        Teatopia.localeEn_Us.item(id(name), localizationEn)
        Teatopia.RESOURCE_PACK.addModel(
            JModel.model(modelParent).textures(JTextures().layer0("${Teatopia.MOD_ID}:item/$name")),
            id("item/$name")
        )
        return registered
    }
}