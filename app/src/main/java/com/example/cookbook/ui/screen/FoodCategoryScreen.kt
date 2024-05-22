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



@Composable
fun FoodCategoryScreen(
    searchQuery: String,
    isSearchingActive: Boolean,
    searchedFoods: List<Food>,
    modifier: Modifier = Modifier,
    foodCategories: List<FoodCategory> = FoodDataProvider.foodCategories,
    onFoodCategoryClick: (Int) -> Unit,
    onSearchedFoodClick: (Food) -> Unit,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit
) {
    Surface(modifier = modifier
        .fillMaxSize())
    {
        Column(
            Modifier
                .background(color = Color(0xFFFFEBB2))
                .padding(15.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "انواع غذا",
                style = MaterialTheme.typography.displaySmall
            )
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = onQueryChange,
                    onSearch = onSearch,
                    active = isSearchingActive,
                    onActiveChange = onActiveChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    trailingIcon = {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                    },
                    placeholder = {
                        Text(text = "جستجو...")
                    }
                ) {

                    LazyColumn {
                        items(searchedFoods) { food ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onSearchedFoodClick(food)
                                    }
                            ) {
                                Text(
                                    text = food.name,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
            LazyColumn {
                items(foodCategories) { foodCategory ->
                    FoodCategoryItem(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        foodCategory = foodCategory,
                        onFoodCategoryClick = onFoodCategoryClick
                    )
                }
            }
        }
    }
}

@Composable
private fun FoodCategoryItem(
    modifier: Modifier = Modifier,
    foodCategory: FoodCategory = FoodDataProvider.foodCategories[0],
    onFoodCategoryClick: (Int) -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(vertical = 4.dp)
            .clickable { onFoodCategoryClick(foodCategory.id) },
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        color = MaterialTheme.colorScheme.tertiaryContainer
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = foodCategory.name,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.titleLarge
            )
            AsyncImage(
                modifier = Modifier.weight(1f),
                model = foodCategory.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Composable
private fun FoodCategoryItemPreview() {
    FoodCategoryItem {}
}
