package com.example.appinsulina.ui

import FoodAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.appinsulina.R
import com.example.appinsulina.data.FoodApi
import com.example.appinsulina.domain.Food
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FoodFragment: Fragment() {
  lateinit var listOfFoods: RecyclerView
  lateinit var btnCalculate: FloatingActionButton
  lateinit var progressBar: ProgressBar
  lateinit var imgNoConnection: ImageView
  lateinit var txtNoConnection: TextView
  lateinit var foodApi: FoodApi

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.food_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupRetrofit()
    setupView(view)
    setupListener()
  }

  override fun onResume() {
    super.onResume()
    getAllFoods()
  }

  fun setupView(view: View) {
    listOfFoods = view.findViewById(R.id.list_foods)
    btnCalculate = view.findViewById(R.id.btn_calculate_insulin)
    progressBar = view.findViewById(R.id.pb_loader)
    imgNoConnection = view.findViewById(R.id.img_no_connection)
    txtNoConnection = view.findViewById(R.id.txt_no_connection)
  }

  fun setupList(list: List<Food>) {
    listOfFoods.isVisible = true
    val adapter = FoodAdapter(list)
    listOfFoods.adapter = adapter
    adapter.onItemClickListener = {
      startActivity(Intent(context, CalculateInsulinActivity::class.java))
    }
  }

  fun setupListener() {
    btnCalculate.setOnClickListener {
      startActivity(Intent(context, CalculateInsulinActivity::class.java))
    }
  }

  fun emptyState() {
    progressBar.isVisible = false
    listOfFoods.isVisible = false
    imgNoConnection.isVisible = true
    txtNoConnection.isVisible = true
  }

  fun setupRetrofit() {
    val retrofit = Retrofit.Builder()
      .baseUrl("https://rodrigonorio.github.io/api-DIO/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    foodApi = retrofit.create(FoodApi::class.java)
  }

  fun getAllFoods() {
    foodApi.getAll().enqueue(object: Callback<List<Food>>{
      override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
        if(response.isSuccessful) {
          progressBar.isVisible = false
          imgNoConnection.isVisible = false
          txtNoConnection.isVisible = false
          response.body()?.let {
            setupList(it)
          }
        } else {
          Toast.makeText(context, R.string.txt_no_connection, Toast.LENGTH_SHORT).show()
        }
      }

      override fun onFailure(call: Call<List<Food>>, t: Throwable) {
        emptyState()
      }
    })
  }
}