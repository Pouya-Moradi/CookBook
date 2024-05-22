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

// Composable function to define navigation structure of the app
@Composable
fun FoodNavHost() {
    // Initialize navigation controller
    val navController = rememberNavController()
    // Initialize view model for managing food-related data
    val foodViewModel = viewModel<FoodViewModel>(factory = FoodViewModel.Factory)

    // Define navigation host with different destinations
    NavHost(navController = navController, startDestination = "category") {

        // Destination: Category screen
        composable("category") {
            // Render FoodCategoryScreen with necessary data and callbacks
            FoodCategoryScreen(
                searchQuery = foodViewModel.searchText.collectAsState().value,
                isSearchingActive = foodViewModel.isSearching.collectAsState().value,
                searchedFoods = foodViewModel.searchedFoods.collectAsState().value,
                onFoodCategoryClick = { categoryId ->
                    navController.navigate("foodList/$categoryId") // Navigate to food list screen with category ID
                },
                onSearchedFoodClick = { food ->
                    navController.navigate("food/${food.id}") // Navigate to individual food screen
                },
                onQueryChange = foodViewModel::onSearchTextChange,
                onSearch = foodViewModel::onSearchTextChange,
                onActiveChange = {
                    foodViewModel.onToggleSearch() // Toggle search mode
                }
            )
        }

        // Destination: Food List screen
        composable(
            "foodList/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Extract category ID from navigation arguments
            val foodCategoryId = backStackEntry.arguments!!.getInt("categoryId")
            // Render FoodListScreen with filtered foods based on category ID
            FoodListScreen(
                foodCategoryId = foodCategoryId,
                foods = foodViewModel.foods.value.filter { food -> food.foodCategoryId == foodCategoryId }
            ) { foodId ->
                navController.navigate("food/$foodId") // Navigate to individual food screen
            }
        }

        // Destination: Individual Food screen
        composable(
            "food/{foodId}",
            arguments = listOf(navArgument("foodId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Extract food ID from navigation arguments and render FoodScreen with corresponding food data
            FoodScreen(food = foodViewModel.getFoodById(backStackEntry.arguments?.getInt("foodId")))
        }
    }
}
