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

    }
}