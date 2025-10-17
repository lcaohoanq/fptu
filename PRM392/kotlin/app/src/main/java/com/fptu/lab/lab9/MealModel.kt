package com.fptu.lab.lab9

data class MealSummary(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String?
)


data class MealListResponse(
    val meals: List<Map<String, String>>?
)


data class MealDetailResponse(
// TheMealDB returns a list with single item in 'meals'
    val meals: List<Map<String, String>>?
)

data class NutritionRequest(val ingredients: List<String>)


data class NutritionNutrient(val label: String, val quantity: Double, val unit: String)


data class NutritionResponse(val calories: Double, val totalWeight: Double, val totalNutrients: Map<String, NutritionNutrient>?)