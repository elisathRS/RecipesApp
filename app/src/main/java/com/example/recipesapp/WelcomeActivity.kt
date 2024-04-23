package com.example.recipesapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONException
import org.json.JSONObject

class WelcomeActivity : AppCompatActivity(), MealAdapter.OnItemClickListener {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var welcomeTextView: TextView
    private lateinit var logoutButton: Button
    private lateinit var mealAdapter: MealAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        mealAdapter = MealAdapter(emptyList(), this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mealAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchRecipe(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    mealAdapter.clearData()
                }
                return false
            }
        })


        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        welcomeTextView = findViewById(R.id.welcomeTextView)
        logoutButton = findViewById(R.id.logoutButton)

        val username = sharedPreferences.getString("username", "")
        "Welcome, $username!".also { welcomeTextView.text = it }

        logoutButton.setOnClickListener {
            sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
            finish()
        }
    }


    private fun searchRecipe(query: String) {
        val client = AsyncHttpClient()
        val url = "https://www.themealdb.com/api/json/v1/1/search.php?s=$query"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, response: JSONObject) {
                try {
                    val mealsArray = response.getJSONArray("meals")
                    val meals = mutableListOf<Meal>()

                    for (i in 0 until mealsArray.length()) {
                        val mealObject = mealsArray.getJSONObject(i)
                        val meal = Meal(
                            idMeal = mealObject.getString("idMeal"),
                            strMeal = mealObject.getString("strMeal"),
                            strCategory = mealObject.getString("strCategory"),
                            strArea = mealObject.getString("strArea"),
                            strInstructions = mealObject.getString("strInstructions"),
                            strMealThumb = mealObject.getString("strMealThumb"),
                            strIngredient1 = mealObject.getString("strIngredient1")
                        )
                        meals.add(meal)
                    }

                    mealAdapter.setData(meals)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                throwable: Throwable,
                errorResponse: JSONObject?
            ) {
                Log.e("MainActivity", "API request failed with status code: $statusCode", throwable)
                Toast.makeText(this@WelcomeActivity, "Failed to fetch data. Please try again later.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemClick(meal: Meal) {
        val intent = Intent(this, MealDetailsActivity::class.java)
        intent.putExtra("meal", meal)
        startActivity(intent)
    }
}
