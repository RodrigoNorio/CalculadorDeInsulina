package com.example.appinsulina.data

import retrofit2.Call
import com.example.appinsulina.domain.Food
import retrofit2.http.GET

interface FoodApi {
  @GET("foods.json")
  fun getAll(): Call<List<Food>>
}