package com.example.cookbook.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cookbook.data.model.Food

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFoods(foods: List<Food>)

    @Query("SELECT * FROM food")
    fun getAllFoods(): List<Food>

}
