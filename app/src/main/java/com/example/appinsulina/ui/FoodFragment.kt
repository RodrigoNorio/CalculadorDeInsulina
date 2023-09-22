package com.example.appinsulina.ui

import FoodAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.appinsulina.R
import com.example.appinsulina.data.FoodFactory
import com.example.appinsulina.domain.Food

class FoodFragment: Fragment() {
  lateinit var listOfFoods: RecyclerView

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.food_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupView(view)
    setupList()
  }

  fun setupView(view: View) {
    listOfFoods = view.findViewById(R.id.list_foods)
  }

  fun setupList() {
    val adapter = FoodAdapter(FoodFactory.list, this)
    listOfFoods.adapter = adapter
  }

  fun onItemClick(food: Food) {
    startActivity(Intent(context, CalculateInsulinActivity::class.java))
  }
}