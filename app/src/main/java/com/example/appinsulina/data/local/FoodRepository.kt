package com.example.appinsulina.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.appinsulina.data.local.FoodContract.FoodEntry.COLUMN_CALORIES
import com.example.appinsulina.data.local.FoodContract.FoodEntry.COLUMN_CARBOHYDRATE
import com.example.appinsulina.data.local.FoodContract.FoodEntry.COLUMN_FOOD_ID
import com.example.appinsulina.data.local.FoodContract.FoodEntry.COLUMN_IMG_URL
import com.example.appinsulina.data.local.FoodContract.FoodEntry.COLUMN_IS_FAVORITE
import com.example.appinsulina.data.local.FoodContract.FoodEntry.COLUMN_NAME
import com.example.appinsulina.data.local.FoodContract.FoodEntry.COLUMN_PROPORTION
import com.example.appinsulina.domain.Food
import java.lang.Exception

class FoodRepository(private val context: Context) {

  fun saveOnDB(food: Food): Boolean {
    var isSaved = false
    try {
      val dbHelper = FoodDBHelper(context)
      val db = dbHelper.writableDatabase
      val values = ContentValues().apply {
        put(COLUMN_FOOD_ID, food.id)
        put(COLUMN_NAME, food.name)
        put(COLUMN_PROPORTION, food.proportion)
        put(COLUMN_CALORIES, food.calories)
        put(COLUMN_CARBOHYDRATE, food.carbohydrate)
        put(COLUMN_IMG_URL, food.urlImg)
        put(COLUMN_IS_FAVORITE, food.isFavorite)
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

  fun findViewByID(id: Int): Food {
    val dbHelper = FoodDBHelper(context)
    val db = dbHelper.readableDatabase
    val columns = arrayOf(
      BaseColumns._ID,
      COLUMN_FOOD_ID,
      COLUMN_NAME,
      COLUMN_PROPORTION,
      COLUMN_CALORIES,
      COLUMN_CARBOHYDRATE,
      COLUMN_IMG_URL,
      COLUMN_IS_FAVORITE
    )
    val filter = "$COLUMN_FOOD_ID = ?"
    val filterValues = arrayOf(id.toString())

    val cursor = db.query(
      FoodContract.FoodEntry.TABLE_NAME,
      columns,
      filter,
      filterValues,
      null,
      null,
      null
    )

    var itemID: Long = 0
    var itemName = ""
    var itemProportion = ""
    var itemCalories = ""
    var itemCarbohydrate = ""
    var itemImgUrl = ""

    with(cursor) {
      while (moveToNext()) {
        itemID = getLong(getColumnIndexOrThrow(COLUMN_FOOD_ID))
        itemName = getString(getColumnIndexOrThrow(COLUMN_NAME))
        itemProportion = getString(getColumnIndexOrThrow(COLUMN_PROPORTION))
        itemCalories = getString(getColumnIndexOrThrow(COLUMN_CALORIES))
        itemCarbohydrate = getString(getColumnIndexOrThrow(COLUMN_CARBOHYDRATE))
        itemImgUrl = getString(getColumnIndexOrThrow(COLUMN_IMG_URL))
      }
    }
    cursor.close()
    return Food(
      id = itemID.toInt(),
      name = itemName,
      proportion = itemProportion,
      calories = itemCalories,
      carbohydrate = itemCarbohydrate,
      urlImg = itemImgUrl,
      isFavorite = true
    )
  }

  fun saveIfNotExist(food: Food) {
    val food = findViewByID(food.id)
    if(food.id == ID_NO_FOOD_EXIST) {
      saveOnDB(food)
    }
  }

  fun getAll(): List<Food> {
    val dbHelper = FoodDBHelper(context)
    val db = dbHelper.readableDatabase
    val columns = arrayOf(
      BaseColumns._ID,
      COLUMN_FOOD_ID,
      COLUMN_NAME,
      COLUMN_PROPORTION,
      COLUMN_CALORIES,
      COLUMN_CARBOHYDRATE,
      COLUMN_IMG_URL,
      COLUMN_IS_FAVORITE
    )

    val foods = mutableListOf<Food>()
    val cursor = db.query(
      FoodContract.FoodEntry.TABLE_NAME,
      columns,
      null,
      null,
      null,
      null,
      null
    )

    with(cursor) {
      while (moveToNext()) {
        val itemID = getLong(getColumnIndexOrThrow(COLUMN_FOOD_ID))
        val itemName = getString(getColumnIndexOrThrow(COLUMN_NAME))
        val itemProportion = getString(getColumnIndexOrThrow(COLUMN_PROPORTION))
        val itemCalories = getString(getColumnIndexOrThrow(COLUMN_CALORIES))
        val itemCarbohydrate = getString(getColumnIndexOrThrow(COLUMN_CARBOHYDRATE))
        val itemImgUrl = getString(getColumnIndexOrThrow(COLUMN_IMG_URL))

        foods.add(
          Food(
            id = itemID.toInt(),
            name = itemName,
            proportion = itemProportion,
            calories = itemCalories,
            carbohydrate = itemCarbohydrate,
            urlImg = itemImgUrl,
            isFavorite = true
          )
        )
      }
    }
    cursor.close()
    return foods
  }
  companion object {
    const val ID_NO_FOOD_EXIST = 0
  }

}