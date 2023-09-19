package com.example.appinsulina

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
  private lateinit var carbohydrate: EditText
  private lateinit var weight: EditText
  private lateinit var glucoseBefore: EditText
  private lateinit var glucoseAfter: EditText
  private lateinit var btnCalculate: Button
  private lateinit var correctionResult: TextView
  private lateinit var mealResult: TextView
  private lateinit var totalResult: TextView
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupView()
    setupListeners()
  }
  fun setupView() {
    carbohydrate = findViewById(R.id.input_grams)
    weight = findViewById(R.id.input_weight)
    glucoseBefore = findViewById(R.id.input_before_glucose)
    glucoseAfter = findViewById(R.id.input_after_glucose)
    btnCalculate = findViewById(R.id.btn_calculate_insulin)
    correctionResult = findViewById(R.id.txt_correction_dose_result)
    mealResult = findViewById(R.id.txt_meal_dose_result)
    totalResult = findViewById(R.id.txt_total_dose_result)
  }
  fun setupListeners() {
    btnCalculate.setOnClickListener{
      calculateInsulin()
    }
  }
  fun calculateInsulin() {
    val inputCarbohydrate = carbohydrate.text.toString().toFloat()
    val inputWeight = weight.text.toString().toFloat()
    val inputPreGlucose = glucoseBefore.text.toString().toFloat()
    val inputPostGlucose = glucoseAfter.text.toString().toFloat()
    var carbohydrateProportion: Double = 0.00

    when {
      inputWeight in 45.00..49.00 -> carbohydrateProportion = 16.00
      inputWeight in 49.50..58.00 -> carbohydrateProportion = 15.00
      inputWeight in 58.50..62.50 -> carbohydrateProportion = 14.00
      inputWeight in 63.00..67.00 -> carbohydrateProportion = 13.00
      inputWeight in 67.50..76.00 -> carbohydrateProportion = 12.00
      inputWeight in 76.50..80.50 -> carbohydrateProportion = 11.00
      inputWeight in 81.00..85.50 -> carbohydrateProportion = 10.00
      inputWeight in 85.50..89.50 -> carbohydrateProportion = 9.00
      inputWeight in 90.00..98.50 -> carbohydrateProportion = 8.00
      inputWeight in 99.00..107.50 -> carbohydrateProportion = 7.00
      inputWeight > 108.00 -> carbohydrateProportion = 6.00
      else -> Toast.makeText(applicationContext, "invalido", Toast.LENGTH_SHORT).show()
    }
    val correctionDose: Double = (inputPreGlucose - inputPostGlucose) / 50.00
    val mealDose: Double = inputCarbohydrate / carbohydrateProportion
    val totalDose: Double = correctionDose + mealDose
    correctionResult.text = getString(R.string.str_correction_dose, "$correctionDose")
    mealResult.text = getString(R.string.str_meal_dose, "$mealDose")
    totalResult.text = getString(R.string.str_total_dose, "$totalDose")
  }
}