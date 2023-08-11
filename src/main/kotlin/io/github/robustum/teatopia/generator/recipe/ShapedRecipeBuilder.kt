package io.github.robustum.teatopia.generator.recipe

class ShapedRecipeBuilder(private val ingredients: List<RecipeIngredient<*>>) {
    private var recipe: MutableList<MutableList<RecipeIngredient<*>?>> =
        mutableListOf(mutableListOf(null, null, null), mutableListOf(null, null, null), mutableListOf(null, null, null))

    operator fun set(index: Int, value: List<RecipeIngredient<*>?>) {
        recipe[index] = value.toMutableList()
    }

    fun set(value: MutableList<MutableList<RecipeIngredient<*>?>>) {
        recipe = value
    }

    fun set(
        i1: RecipeIngredient<*>?,
        i2: RecipeIngredient<*>?,
        i3: RecipeIngredient<*>?,
        i4: RecipeIngredient<*>?,
        i5: RecipeIngredient<*>?,
        i6: RecipeIngredient<*>?,
        i7: RecipeIngredient<*>?,
        i8: RecipeIngredient<*>?,
        i9: RecipeIngredient<*>?,
    ) {
        recipe[0] = mutableListOf(i1, i2, i3)
        recipe[1] = mutableListOf(i4, i5, i6)
        recipe[2] = mutableListOf(i7, i8, i9)
    }

    fun build(): Pair<List<List<Char>>, Map<Char, RecipeIngredient<*>>> {
        val (normal, reverse) = ingredients.mapIndexed { index, recipeIngredient ->
            index.toString()[0] to recipeIngredient
        }.let { list -> list.toMap() to list.associate { it.second to it.first } }
        val recipe = recipe.map { list -> list.map { ingredient -> ingredient?.let { reverse.getValue(it) } ?: ' ' } }
        return recipe to normal
    }
}