package com.example.appinsulina.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.appinsulina.R
import com.example.appinsulina.ui.adapter.TabsAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
  lateinit var tabLayout: TabLayout
  lateinit var viewPager2: ViewPager2
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupView()
    setupTabs()
  }
  fun setupView() {
    tabLayout = findViewById(R.id.tab_layout)
    viewPager2 = findViewById(R.id.food_tab)
  }

  fun setupTabs() {
    val tabsAdapter = TabsAdapter(this)
    viewPager2.adapter = tabsAdapter

    tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
      override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.let {
          viewPager2.currentItem = it.position
        }
      }

      override fun onTabUnselected(tab: TabLayout.Tab?) {

      }

      override fun onTabReselected(tab: TabLayout.Tab?) {

      }
    })

    viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
      override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        tabLayout.getTabAt(position)?.select()
      }
    })
  }
}