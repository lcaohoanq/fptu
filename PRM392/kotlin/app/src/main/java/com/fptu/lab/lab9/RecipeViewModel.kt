package com.fptu.lab.lab9

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeViewModel(private val repo: RecipeRepository = RecipeRepository()) : ViewModel() {
    var query by mutableStateOf("")
        private set
    
    var recipes by mutableStateOf<List<MealSummary>>(emptyList())
        private set
    
    var isLoading by mutableStateOf(false)
        private set
    
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onQueryChange(newQuery: String) {
        query = newQuery
    }

    fun search() {
        if (query.isBlank()) {
            errorMessage = "Please enter a search query"
            return
        }
        
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            val result = withContext(Dispatchers.IO) { repo.searchRecipes(query) }
            isLoading = false
            
            result.fold(
                onSuccess = { list ->
                    if (list.isEmpty()) {
                        errorMessage = "No results found"
                        recipes = emptyList()
                    } else {
                        recipes = list
                    }
                },
                onFailure = { e ->
                    errorMessage = e.localizedMessage ?: "Network error"
                    recipes = emptyList()
                }
            )
        }
    }

    // Fetch recipe detail and nutrition together
    fun fetchDetailAndNutrition(id: String, onResult: (Result<Pair<Map<String, String>, NutritionResponse?>>) -> Unit) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            val detailR = withContext(Dispatchers.IO) { repo.getRecipeDetail(id) }
            detailR.fold(onSuccess = { detailMap ->
                // Extract ingredients from detailMap (TheMealDB returns fields strIngredient1..20)
                val ingredients = (1..20).mapNotNull { idx ->
                    val ing = detailMap["strIngredient$idx"]?.trim()
                    val measure = detailMap["strMeasure$idx"]?.trim()
                    if (!ing.isNullOrBlank()) {
                        if (!measure.isNullOrBlank()) "${measure} ${ing}" else ing
                    } else null
                }

                // Try nutrition API, but if it fails return null for nutrition
                val nutritionR = try {
                    withContext(Dispatchers.IO) { repo.analyzeNutrition(ingredients) }
                } catch (e: Exception) {
                    Result.failure<NutritionResponse>(e)
                }

                isLoading = false

                if (nutritionR.isSuccess) {
                    onResult(Result.success(Pair(detailMap, nutritionR.getOrNull())))
                } else {
                    // Return detail but with null nutrition; still success
                    onResult(Result.success(Pair(detailMap, null)))
                }

            }, onFailure = { e ->
                isLoading = false
                onResult(Result.failure(e))
            })
        }
    }
}