package com.example.cookbook.ui.screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.cookbook.FoodApplication
import com.example.cookbook.data.FoodDataProvider
import com.example.cookbook.data.FoodRepository
import com.example.cookbook.data.model.Food
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn


// ViewModel for managing data related to food
class FoodViewModel(
    foodRepository: FoodRepository // Repository for accessing food data
): ViewModel() {
    // Factory for creating instances of FoodViewModel
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            // Function to create FoodViewModel instance
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY]) // Accessing application context
                return FoodViewModel(
                    (application as FoodApplication).foodRepository // Creating FoodViewModel instance
                ) as T
            }
        }
    }

    // Mutable state flow for holding search text
    val searchText = MutableStateFlow("")
    // Mutable state flow for tracking search status
    val isSearching = MutableStateFlow(false)

    // Function to update search text
    fun onSearchTextChange(text: String) {
        searchText.value = text
    }

    // Function to toggle search status
    fun onToggleSearch() {
        isSearching.value = !isSearching.value
        if (!isSearching.value)
            onSearchTextChange("")
    }

    // State holding list of foods
    val foods: State<List<Food>> = mutableStateOf(
        foodRepository.getFoods() // Initializing with list of foods from repository
    )

    // Mutable state flow for holding searched foods
    private val _searchedFoods = MutableStateFlow(foods.value)
    // State flow for filtering foods based on search text
    val searchedFoods = searchText.combine(_searchedFoods) { text, foods ->
        if (text.isBlank())
            emptyList() // Return empty list if search text is blank
        else {
            foods.filter { food ->
                food.name.uppercase().startsWith(text.trim().uppercase()) // Filter foods based on search text
            }
        }
    }.stateIn(
        scope = viewModelScope, // Scope for coroutine
        started = SharingStarted.WhileSubscribed(5000), // Sharing strategy
        initialValue = _searchedFoods.value // Initial value
    )

    // Function to get food by ID
    fun getFoodById(id: Int?): Food {
        return foods.value.find { food -> food.id == id } ?: FoodDataProvider.foods[0] // Finding food by ID
    }
}