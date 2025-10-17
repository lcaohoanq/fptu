package com.fptu.lab.lab9

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun SearchScreen(viewModel: RecipeViewModel, onOpenDetail: (String) -> Unit) {
    val context = LocalContext.current
    val q by remember { derivedStateOf { viewModel.query } }


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = q,
            onValueChange = { viewModel.onQueryChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search recipes") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.search() }, modifier = Modifier.align(Alignment.End)) {
            Text("Search")
        }


        if (viewModel.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Column
        }


        viewModel.errorMessage?.let { err ->
            Text(text = err, color = MaterialTheme.colors.error, modifier = Modifier.padding(8.dp))
        }


        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(viewModel.recipes) { item ->
                RecipeListItem(item) { onOpenDetail(item.idMeal) }
            }
        }
    }
}


@Composable
fun RecipeListItem(item: MealSummary, onClick: () -> Unit) {
    Card(modifier = Modifier
        .padding(vertical = 8.dp)
        .fillMaxWidth()
        .clickable { onClick() }) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(model = item.strMealThumb, contentDescription = item.strMeal, modifier = Modifier.size(72.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = item.strMeal, fontWeight = FontWeight.Bold)
                Text(text = "ID: ${item.idMeal}")
            }
        }
    }
}

@Composable
fun RecipeDetailScreen(viewModel: RecipeViewModel, recipeId: String, onBack: () -> Unit) {
    var detailData by remember { mutableStateOf<Pair<Map<String, String>, NutritionResponse?>?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(recipeId) {
        viewModel.fetchDetailAndNutrition(recipeId) { result ->
            result.fold(
                onSuccess = { data -> detailData = data },
                onFailure = { e -> errorMessage = e.localizedMessage ?: "Error loading details" }
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBack) {
            Text("Back")
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Column
        }

        errorMessage?.let { err ->
            Text(text = err, color = MaterialTheme.colors.error)
            return@Column
        }

        detailData?.let { (detail, nutrition) ->
            LazyColumn {
                item {
                    AsyncImage(
                        model = detail["strMealThumb"],
                        contentDescription = detail["strMeal"],
                        modifier = Modifier.fillMaxWidth().height(200.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = detail["strMeal"] ?: "Unknown", style = MaterialTheme.typography.h5)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Category: ${detail["strCategory"] ?: "N/A"}")
                    Text(text = "Area: ${detail["strArea"] ?: "N/A"}")
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Instructions:", style = MaterialTheme.typography.h6)
                    Text(text = detail["strInstructions"] ?: "No instructions available")
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(text = "Ingredients:", style = MaterialTheme.typography.h6)
                    (1..20).forEach { idx ->
                        val ing = detail["strIngredient$idx"]?.trim()
                        val measure = detail["strMeasure$idx"]?.trim()
                        if (!ing.isNullOrBlank()) {
                            Text(text = "• ${measure ?: ""} ${ing}")
                        }
                    }
                    
                    nutrition?.let { nutr ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Nutrition:", style = MaterialTheme.typography.h6)
                        Text(text = "Calories: ${nutr.calories}")
                        Text(text = "Total Weight: ${nutr.totalWeight}g")
                    }
                }
            }
        }
    }
}