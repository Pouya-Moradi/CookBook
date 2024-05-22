package com.example.cookbook.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cookbook.data.FoodDataProvider
import com.example.cookbook.data.model.Food

@Composable
fun FoodListScreen(
    modifier: Modifier = Modifier,
    foodCategoryId: Int,
    foods: List<Food>,
    onFoodItemClick: (Int) -> Unit
) {
    val foodCategory = FoodDataProvider.foodCategories.find { foodCategory -> foodCategory.id == foodCategoryId }
    Surface(modifier = modifier
        .fillMaxSize()
        .background(color = Color(0xFFF9E4C8)),
        color = Color(0xFFF9E4C8)) {
        Column(
            Modifier.padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = modifier.padding(50.dp),
                text = foodCategory!!.name,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center // تراز کردن متن به وسط
            )


            //Spacer(modifier = Modifier.height(15.dp)) // فاصله بین تابع Text و LazyColumn

            LazyColumn {
                items(foods, key = { food -> food.id }) { food ->
                    FoodListItem(
                        food = food,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onFoodItemClick
                    )
                }
            }
        }
    }
}

@Composable
private fun FoodListItem(
    food: Food,
    modifier: Modifier = Modifier,
    onFoodItemClick: (Int) -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)

            .padding(vertical = 4.dp, horizontal = 12.dp)
            .clickable {
                onFoodItemClick(food.id)
            },
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        color = Color(0xFFF9CF93)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = food.name,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.titleLarge
            )
            AsyncImage(
                modifier = Modifier.weight(1f),
                model = food.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
        }
    }
}