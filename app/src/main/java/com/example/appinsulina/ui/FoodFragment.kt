package com.example.appinsulina.ui

import FoodAdapter
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.JsonToken
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.appinsulina.R
import com.example.appinsulina.data.FoodFactory
import com.example.appinsulina.domain.Food
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class FoodFragment: Fragment() {
  lateinit var listOfFoods: RecyclerView
  lateinit var btnCalculate: FloatingActionButton
  lateinit var progressBar: ProgressBar

  var foodsArray: ArrayList<Food> = ArrayList()

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
    callService()
    setupListener()
  }

  fun setupView(view: View) {
    listOfFoods = view.findViewById(R.id.list_foods)
    btnCalculate = view.findViewById(R.id.btn_calculate_insulin)
    progressBar = view.findViewById(R.id.pb_loader)
  }

  fun setupList() {
    listOfFoods.visibility = View.VISIBLE
    val adapter = FoodAdapter(foodsArray, this)
    listOfFoods.adapter = adapter
  }

  fun onItemClick(food: Food) {
    startActivity(Intent(context, CalculateInsulinActivity::class.java))
  }

  fun setupListener() {
    btnCalculate.setOnClickListener {
      startActivity(Intent(context, CalculateInsulinActivity::class.java))
    }
  }

  fun callService() {
    val urlBase = "https://rodrigonorio.github.io/api-DIO/foods.json"
    getFoodInfo().execute(urlBase)
  }

  inner class getFoodInfo: AsyncTask<String, String, String>() {
    override fun onPreExecute() {
      super.onPreExecute()
      progressBar.visibility = View.VISIBLE
    }
    override fun doInBackground(vararg url: String?): String {
      var urlConnection: HttpURLConnection? = null
      try {
        val urlBase = URL(url[0])
        urlConnection = urlBase.openConnection() as HttpURLConnection
        urlConnection.connectTimeout = 60000
        urlConnection.readTimeout = 60000
        urlConnection.setRequestProperty(
          "Accept",
          "application/json"
        )
        val responseCode = urlConnection.responseCode

        if(responseCode == HttpURLConnection.HTTP_OK) {
          var response = urlConnection.inputStream.bufferedReader().use {it.readText()}
          publishProgress(response)
        } else {
          Log.e("Erro", "Service shutdown")
        }
      } catch (ex: Exception) {
        Log.e("Erro", "Error on connection")
      } finally {
        urlConnection?.disconnect()
      }
      return ""
    }
    override fun onProgressUpdate(vararg values: String?) {
      try {
        val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray
        for(i in 0 until jsonArray.length()) {
          val id = jsonArray.getJSONObject(i).getString("id")
          val name = jsonArray.getJSONObject(i).getString("name")
          val proportion = jsonArray.getJSONObject(i).getString("proportion")
          val calories = jsonArray.getJSONObject(i).getString("calories")
          val carbohydrate = jsonArray.getJSONObject(i).getString("carbohydrate")
          val urlImg = jsonArray.getJSONObject(i).getString("urlImg")

          val foodModel = Food(
            id = id.toInt(),
            name = name,
            proportion = proportion,
            calories = calories,
            carbohydrate = carbohydrate,
            urlImg = urlImg
          )
          foodsArray.add(foodModel)
        }
        progressBar.visibility = View.GONE
        setupList()
      } catch (ex: Exception) {
        Log.e("Erro", ex.message.toString())
      }
    }
  }
}