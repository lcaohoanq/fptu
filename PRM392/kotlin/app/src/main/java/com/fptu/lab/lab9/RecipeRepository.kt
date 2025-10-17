package com.fptu.lab.lab9

class RecipeRepository(
    private val mealApi: MealApiService = RetrofitClient.mealApi,
    private val nutritionApi: NutritionApiService = RetrofitClient.nutritionApi
) {
    suspend fun searchRecipes(query: String): Result<List<MealSummary>> {
        return try {
            val resp = mealApi.searchMeals(query)
            val meals = resp.meals?.mapNotNull { map ->
                val id = map["idMeal"] ?: return@mapNotNull null
                val name = map["strMeal"] ?: return@mapNotNull null
                val thumb = map["strMealThumb"]
                MealSummary(idMeal = id, strMeal = name, strMealThumb = thumb)
            } ?: emptyList()
            Result.success(meals)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun getRecipeDetail(id: String): Result<Map<String, String>> {
        return try {
            val resp = mealApi.lookupMeal(id)
            val item = resp.meals?.firstOrNull()
            if (item != null) Result.success(item) else Result.failure(Exception("Not found"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun analyzeNutrition(ingredients: List<String>): Result<NutritionResponse> {
        return try {
// For lab, if you don't have real nutrition API keys you can return fake data here
            val resp = nutritionApi.analyzeNutrition(NutritionRequest(ingredients))
            Result.success(resp)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}