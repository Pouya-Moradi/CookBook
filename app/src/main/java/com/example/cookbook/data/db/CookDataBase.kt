package com.example.cookbook.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cookbook.data.model.Food

@Database([Food::class], version = 1)
abstract class CookDataBase: RoomDatabase() {

    private var cookDatabase: CookDataBase? = null
    abstract fun foodDao(): FoodDao

    companion object {
        @Volatile
        private var INSTANCE: CookDataBase? = null

        fun getInstance(context: Context): CookDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    CookDataBase::class.java,
                    "cook-db"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
