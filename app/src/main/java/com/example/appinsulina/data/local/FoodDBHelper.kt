package com.example.appinsulina.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appinsulina.data.local.FoodContract.SQL_DELETE_ENTRIES
import com.example.appinsulina.data.local.FoodContract.TABLE_FOOD


class FoodDBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
  override fun onCreate(db: SQLiteDatabase?) {
    db?.execSQL(TABLE_FOOD)
  }

  override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    db?.execSQL(SQL_DELETE_ENTRIES)
    onCreate(db)
  }

  override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    onUpgrade(db, oldVersion, newVersion)
  }

  companion object {
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "DbAppInsulin.db"
  }
}