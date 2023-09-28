package com.example.appinsulina.data.local

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.appinsulina.domain.Food
import java.lang.Exception

class FoodRepository(private val context: Context) {

  fun saveOnDB(food: Food): Boolean {
    var isSaved = false
    try {
      val dbHelper = FoodDBHelper(context)
      val db = dbHelper.writableDatabase
      val values = ContentValues().apply {
        put(FoodContract.FoodEntry.COLUMN_NAME, food.name)
        put(FoodContract.FoodEntry.COLUMN_PROPORTION, food.proportion)
        put(FoodContract.FoodEntry.COLUMN_CALORIES, food.calories)
        put(FoodContract.FoodEntry.COLUMN_CARBOHYDRATE, food.carbohydrate)
        put(FoodContract.FoodEntry.COLUMN_IMG_URL, food.urlImg)
        put(FoodContract.FoodEntry.COLUMN_IS_FAVORITE, food.isFavorite)
      }
      val insert = db?.insert(FoodContract.FoodEntry.TABLE_NAME, null, values)
      if (insert != null) {
        isSaved = true
      }
    } catch (ex: Exception) {
      ex.message?.let {
        Log.e("Error db connection", it)
      }
    }
    return isSaved
  }
  
}