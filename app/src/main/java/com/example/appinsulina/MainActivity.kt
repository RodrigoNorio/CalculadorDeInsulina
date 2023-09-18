package com.example.appinsulina

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

  private lateinit var carbohydrate: EditText
  private lateinit var weight: EditText
  lateinit var btnCalculate: Button
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupView()
    setupListeners()
  }

  fun setupView() {
    carbohydrate = findViewById(R.id.input_grams)
    weight = findViewById(R.id.input_weight)
    btnCalculate = findViewById(R.id.btn_calculate_insulina)
  }

  fun setupListeners() {
    btnCalculate.setOnClickListener{
      val inputCarbohydrate = carbohydrate.text.toString()
      val inputWeight = weight.text.toString()
      Toast.makeText(applicationContext, inputCarbohydrate, Toast.LENGTH_SHORT).show()
    }
  }
}