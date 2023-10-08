package com.example.appinsulina.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.appinsulina.R
import com.example.appinsulina.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    setupListener()

    val navController = findNavController(R.id.nav_host_fragment)
    NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
  }

  fun setupListener() {
    binding.btnCalculateInsulin.setOnClickListener {
      startActivity(Intent(this, CalculateInsulinActivity::class.java))
    }
  }

  /*
  @TODO foods adicionadas nos favoritos nao aparecem imediatamente ao clicar na estrela,
     é necessário fechar o app e abri- lo denovo para mostrar na lista de favoritos
   */
}