package io.github.robustum.teatopia.generator.recipe

import net.devtech.arrp.api.RuntimeResourcePack
import net.devtech.arrp.json.recipe.*
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

class RecipeGenerator(private val resourcePack: RuntimeResourcePack) {

    fun addShapedRecipe(
        id: Identifier,
        ingredients: List<RecipeIngredient<*>>,
        output: ItemStack,
        configure: ShapedRecipeBuilder.(List<RecipeIngredient<*>>) -> Unit,
    ) {
        val (pattern, patternMap) = ShapedRecipeBuilder(ingredients).apply { configure(ingredients) }.build()
        val recipe = JRecipe.shaped(
            JPattern.pattern(*(pattern.map { it.joinToString(separator = "") }
                .toTypedArray())),
            JKeys.keys().apply {
                patternMap.mapValues { (_, ingredient) ->
                    ingredient.toJIngredient()
                }.forEach { (k, ingredient) ->
                    key(k.toString(), ingredient)
                }
            },
            JResult.itemStack(output.item, output.count),
        )
        resourcePack.addRecipe(id, recipe)
    }

    fun addShapelessRecipe(
        id: Identifier,
        ingredients: List<RecipeIngredient<*>>,
        output: ItemStack,
    ) {
        val recipe = JRecipe.shapeless(
            JIngredients.ingredients().apply { ingredients.forEach { add(it.toJIngredient()) } },
            JResult.itemStack(output.item, output.count)
        )
        resourcePack.addRecipe(id, recipe)
    }

    fun addShapelessCompressRecipe(id: Identifier, count: Int, input: RecipeIngredient<*>, output: ItemStack) {
        val list = buildList {
            repeat(count) {
                add(input)
            }
        }
        addShapelessRecipe(id, list, output)
    }

    fun addShapelessDecompressRecipe(id: Identifier, input: RecipeIngredient<*>, output: ItemStack) {
        addShapelessRecipe(id, listOf(input), output)
    }

    fun addShapelessReversableCompressRecipe(id: Identifier, count: Int, small: Item, large: Item) {
        addShapelessCompressRecipe(
            Identifier(id.namespace, "${id.path}_up"),
            count,
            RecipeIngredient.Item(small),
            ItemStack(large)
        )
        addShapelessDecompressRecipe(
            Identifier(id.namespace, "${id.path}_down"),
            RecipeIngredient.Item(large),
            ItemStack(small, count)
        )
    }

    private fun RecipeIngredient<*>.toJIngredient(): JIngredient? =
        when (this) {
            is RecipeIngredient.Item -> JIngredient.ingredient().item(content)
            is RecipeIngredient.Tag -> JIngredient.ingredient().tag(content)
        }
}
