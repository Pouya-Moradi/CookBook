package com.example.cookbook.ui.screen


//import android.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cookbook.data.FoodDataProvider
import com.example.cookbook.data.model.Food
import com.example.cookbook.data.model.FoodCategory



// Composable function for displaying the food category screen
@Composable
fun FoodCategoryScreen(
    searchQuery: String, // Query string for search functionality
    isSearchingActive: Boolean, // Flag indicating if search is active
    searchedFoods: List<Food>, // List of searched foods
    modifier: Modifier = Modifier, // Modifier for styling
    foodCategories: List<FoodCategory> = FoodDataProvider.foodCategories, // List of food categories
    onFoodCategoryClick: (Int) -> Unit, // Callback for when a food category is clicked
    onSearchedFoodClick: (Food) -> Unit, // Callback for when a searched food is clicked
    onQueryChange: (String) -> Unit, // Callback for query text change
    onSearch: (String) -> Unit, // Callback for search action
    onActiveChange: (Boolean) -> Unit // Callback for search activation status change
) {
    // Surface for holding the entire screen content
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            Modifier
                .background(color = Color(0xFFFFEBB2)) // Background color
                .padding(15.dp), // Padding for content

            horizontalAlignment = Alignment.CenterHorizontally // Center align content horizontally
        ) {
            // Title text for food categories
            Text(
                text = "انواع غذا", // Text content in Persian
                style = MaterialTheme.typography.displaySmall // Typography style
            )

            // Provide local layout direction for RTL support
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                // Search bar for searching foods
                SearchBar(
                    query = searchQuery, // Current search query
                    onQueryChange = onQueryChange, // Callback for query change
                    onSearch = onSearch, // Callback for search action
                    active = isSearchingActive, // Flag indicating search activation status
                    onActiveChange = onActiveChange, // Callback for search activation status change
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp), // Modifier for styling
                    trailingIcon = {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = null) // Search icon
                    },
                    placeholder = {
                        Text(text = "جستجو...") // Placeholder text in Persian
                    }
                ) {
                    // Lazy column for displaying searched foods
                    LazyColumn {
                        items(searchedFoods) { food ->
                            // Surface for individual searched food item
                            Surface(
                                modifier = Modifier.fillMaxWidth().clickable { onSearchedFoodClick(food) }
                            ) {
                                // Text for displaying food name
                                Text(
                                    text = food.name,
                                    modifier = Modifier.padding(8.dp) // Modifier for styling
                                )
                            }
                        }
                    }
                }
            }

            // Lazy column for displaying food categories
            LazyColumn {
                items(foodCategories) { foodCategory ->
                    // Composable function for individual food category item
                    FoodCategoryItem(
                        modifier = Modifier.padding(horizontal = 8.dp), // Modifier for styling
                        foodCategory = foodCategory, // Food category object
                        onFoodCategoryClick = onFoodCategoryClick // Callback for food category click
                    )
                }
            }
        }
    }
}

// Composable function for displaying individual food category item
@Composable
private fun FoodCategoryItem(
    modifier: Modifier = Modifier, // Modifier for styling
    foodCategory: FoodCategory = FoodDataProvider.foodCategories[0], // Default food category
    onFoodCategoryClick: (Int) -> Unit // Callback for food category click
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(vertical = 4.dp)
            //.border(BorderStroke(2.dp, MaterialTheme.colorScheme.primary)) // applying stroke
            .clip(RoundedCornerShape(25)) // Round corners
            .clickable { onFoodCategoryClick(foodCategory.id) }, // Clickable behavior
        color = Color() // Background color
    ) {
        // Row for aligning category name and image
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Text for displaying category name
            Text(
                text = foodCategory.name, // Name of the food category
                modifier = Modifier.weight(1f), // Weight for layout
                textAlign = TextAlign.Center, // Text alignment
                color = MaterialTheme.colorScheme.onTertiaryContainer, // Text color
                style = MaterialTheme.typography.titleLarge // Typography style
            )

            // AsyncImage for displaying category image
            AsyncImage(
                modifier = Modifier.weight(1f), // Weight for layout
                model = foodCategory.imageUrl, // Image URL
                contentDescription = null, // Description for accessibility
                contentScale = ContentScale.FillBounds // Scale type for content
            )
        }
    }
}

// Preview function for FoodCategoryItem composable
@Preview
@Composable
private fun FoodCategoryItemPreview() {
    FoodCategoryItem {} // Preview of FoodCategoryItem
}