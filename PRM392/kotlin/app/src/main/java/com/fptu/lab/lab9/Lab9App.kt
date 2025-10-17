package com.fptu.lab.lab9

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController

@Composable
fun Lab9App() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val viewModel: RecipeViewModel = viewModel()
    
    MaterialTheme {
        Surface {
            NavigationComposable(
                context = context,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}
