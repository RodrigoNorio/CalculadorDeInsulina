package com.example.appinsulina.ui

import FoodAdapter
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.appinsulina.R
import com.example.appinsulina.domain.Food
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONTokener
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class FoodFragment: Fragment() {
  lateinit var listOfFoods: RecyclerView
  lateinit var btnCalculate: FloatingActionButton
  lateinit var progressBar: ProgressBar
  lateinit var imgNoConnection: ImageView
  lateinit var txtNoConnection: TextView

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
    setupListener()
  }

  override fun onResume() {
    super.onResume()
    if(checkNetwork(context)) {
      callService()
    } else{
      emptyState()
    }
  }

  fun setupView(view: View) {
    listOfFoods = view.findViewById(R.id.list_foods)
    btnCalculate = view.findViewById(R.id.btn_calculate_insulin)
    progressBar = view.findViewById(R.id.pb_loader)
    imgNoConnection = view.findViewById(R.id.img_no_connection)
    txtNoConnection = view.findViewById(R.id.txt_no_connection)
  }

  fun setupList() {
    listOfFoods.isVisible = true
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

  fun emptyState() {
    progressBar.isVisible = false
    listOfFoods.isVisible = false
    imgNoConnection.isVisible = true
    txtNoConnection.isVisible = true
  }

  fun checkNetwork(context: Context?): Boolean {
    val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      val network = connectivityManager.activeNetwork ?: return false
      val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

      return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
      }
    } else {
      @Suppress("Deprecated")
      val networkInfo = connectivityManager.activeNetworkInfo ?: return false
      @Suppress("Deprecated")
      return networkInfo.isConnected
    }
  }

  inner class getFoodInfo: AsyncTask<String, String, String>() {
    override fun onPreExecute() {
      super.onPreExecute()
      progressBar.isVisible = true
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
          val response = urlConnection.inputStream.bufferedReader().use {it.readText()}
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
        progressBar.isVisible = false
        imgNoConnection.isVisible = false
        txtNoConnection.isVisible = false
        setupList()
      } catch (ex: Exception) {
        Log.e("Erro", ex.message.toString())
      }
    }
  }
}