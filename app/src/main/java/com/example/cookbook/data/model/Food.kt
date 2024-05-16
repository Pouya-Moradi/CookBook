package com.example.cookbook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val imageUrl: String,
    val foodCategoryId: Int,
    val recipe: String
)

data class FoodCategory(
    val id: Int,
    val name: String,
    val imageUrl: String
)
