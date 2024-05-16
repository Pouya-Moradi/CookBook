package com.example.cookbook.data

import com.example.cookbook.data.db.FoodDao
import com.example.cookbook.data.model.Food

class FoodRepository(
    private val foodDao: FoodDao
) {
    fun getFoods(): List<Food> = foodDao.getAllFoods()
}