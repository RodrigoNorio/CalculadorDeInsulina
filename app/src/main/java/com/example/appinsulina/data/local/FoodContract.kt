package com.example.appinsulina.data.local

import android.provider.BaseColumns

object FoodContract {

  object FoodEntry: BaseColumns {
    const val TABLE_NAME = "food"
    const val COLUMN_ID = "id"
    const val COLUMN_FOOD_ID = "id"
    const val COLUMN_NAME = "name"
    const val COLUMN_PROPORTION = "proportion"
    const val COLUMN_CALORIES = "calories"
    const val COLUMN_CARBOHYDRATE = "carbohydrate"
    const val COLUMN_IMG_URL = "urlImg"
    const val COLUMN_IS_FAVORITE = "isFavorite"
  }
  const val TABLE_FOOD =
    "CREATE TABLE ${FoodEntry.TABLE_NAME} (" +
      "${BaseColumns._ID} INTEGER PRIMARY KEY," +
      "${FoodEntry.COLUMN_FOOD_ID} TEXT," +
      "${FoodEntry.COLUMN_NAME} TEXT," +
      "${FoodEntry.COLUMN_PROPORTION} TEXT," +
      "${FoodEntry.COLUMN_CALORIES} TEXT," +
      "${FoodEntry.COLUMN_CARBOHYDRATE} TEXT," +
      "${FoodEntry.COLUMN_IMG_URL} TEXT," +
      "${FoodEntry.COLUMN_IS_FAVORITE} BOOLEAN)"

  const val SQL_DELETE_ENTRIES =
    "DROP TABLE IF EXISTS ${FoodEntry.TABLE_NAME}"
}