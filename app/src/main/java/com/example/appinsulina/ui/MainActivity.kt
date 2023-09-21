package com.example.appinsulina.ui

import FoodAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.appinsulina.R
import com.example.appinsulina.data.FoodFactory
import com.example.appinsulina.domain.Food

class MainActivity : AppCompatActivity() {
  lateinit var listOfFoods: RecyclerView
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupView()
    setupListeners()
    setupList()
  }
  fun setupView() {
    listOfFoods = findViewById(R.id.list_foods)
  }
  fun setupListeners() {

  }
  fun setupList() {
    val adapter = FoodAdapter(FoodFactory.list, this)
    listOfFoods.adapter = adapter
  }

  fun onItemClick(food: Food) {
    startActivity(Intent(this, CalculateInsulinActivity::class.java))
  }
}