package com.example.appinsulina.ui

import FoodAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.appinsulina.R
import com.example.appinsulina.data.local.FoodRepository
import com.example.appinsulina.domain.Food

class FavoritesFragment: Fragment() {
  lateinit var listOfFavorites: RecyclerView
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.favourite_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupView(view)
    setupList()
  }

  fun setupView(view: View) {
    listOfFavorites = view.findViewById(R.id.list_favorite)
  }

  fun setupList() {
    val favoriteList = getFavoriteFoods()
    listOfFavorites.isVisible = true
    val adapter = FoodAdapter(favoriteList)
    listOfFavorites.adapter = adapter
  }

  fun getFavoriteFoods(): List<Food> {
    val repository = FoodRepository(requireContext())
    return repository.getAll()
  }
}