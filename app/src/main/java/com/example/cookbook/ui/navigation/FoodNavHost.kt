package com.example.cookbook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cookbook.data.FoodDataProvider
import com.example.cookbook.ui.screen.FoodCategoryScreen
import com.example.cookbook.ui.screen.FoodListScreen
import com.example.cookbook.ui.screen.FoodScreen
import com.example.cookbook.ui.screen.FoodViewModel

@Composable
fun FoodNavHost() {
    val navController = rememberNavController()
    val foodViewModel = viewModel<FoodViewModel>(factory = FoodViewModel.Factory)
    NavHost(navController = navController, startDestination = "category") {
        composable("category") {
            FoodCategoryScreen(
                searchQuery = foodViewModel.searchText.collectAsState().value,
                isSearchingActive = foodViewModel.isSearching.collectAsState().value,
                searchedFoods = foodViewModel.searchedFoods.collectAsState().value,
                onFoodCategoryClick = { categoryId ->
                    navController.navigate("foodList/$categoryId")
                },
                onSearchedFoodClick = { food ->
                    navController.navigate("food/${food.id}")
                },
                onQueryChange = foodViewModel::onSearchTextChange,
                onSearch = foodViewModel::onSearchTextChange,
                onActiveChange = {
                    foodViewModel.onToggleSearch()
                }
            )

            /* { categoryId ->
                navController.navigate("foodList/$categoryId")
            }*/
        }
        composable(
            "foodList/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) { backStackEntry ->
            val foodCategoryId = backStackEntry.arguments!!.getInt("categoryId")
            FoodListScreen(
                foodCategoryId = foodCategoryId,
                foods = foodViewModel.foods.value.filter { food -> food.foodCategoryId == foodCategoryId }
            ) { foodId ->
                navController.navigate("food/$foodId")
            }
        }
        composable(
            "food/{foodId}",
            arguments = listOf(navArgument("foodId") { type = NavType.IntType })
        ) { backStackEntry ->
            FoodScreen(food = foodViewModel.getFoodById(backStackEntry.arguments?.getInt("foodId")))
        }
    }
}