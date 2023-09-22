package com.example.appinsulina.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appinsulina.ui.FavoritesFragment
import com.example.appinsulina.ui.FoodFragment

class TabsAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
  override fun getItemCount(): Int {
    return 2
  }

  override fun createFragment(position: Int): Fragment {
    return when(position) {
      0 -> FoodFragment()
      1 -> FavoritesFragment()
      else -> FoodFragment()
    }
  }
}