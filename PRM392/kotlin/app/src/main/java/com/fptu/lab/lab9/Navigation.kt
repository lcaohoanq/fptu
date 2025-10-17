package com.fptu.lab.lab9

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

interface Destinations {
    val route: String
}


object SearchScreenDest : Destinations { override val route = "Search" }
object DetailScreenDest : Destinations { override val route = "Detail/{recipeId}" }
object DetailScreenDestId { fun routeWithId(id: String) = "Detail/$id" }


@Composable
fun NavigationComposable(context: Context, navController: NavHostController, viewModel: RecipeViewModel) {
    NavHost(navController = navController, startDestination = SearchScreenDest.route) {
        composable(SearchScreenDest.route) {
            SearchScreen(viewModel = viewModel, onOpenDetail = { id ->
                navController.navigate(DetailScreenDestId.routeWithId(id))
            })
        }
        composable("Detail/{recipeId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("recipeId") ?: ""
            RecipeDetailScreen(viewModel = viewModel, recipeId = id, onBack = { navController.popBackStack() })
        }
    }
}