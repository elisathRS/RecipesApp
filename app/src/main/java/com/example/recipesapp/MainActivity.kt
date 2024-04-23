package com.example.recipesapp

/*
import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity(), MealAdapter.OnItemClickListener {

    private lateinit var mealAdapter: MealAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                return false
            }
        })
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
                // Handle failure
            }
        })
    }

    override fun onItemClick(meal: Meal) {
        val intent = Intent(this, MealDetailsActivity::class.java)
        intent.putExtra("meal", meal)
        startActivity(intent)
    }
}

 */

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)


        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            navigateToWelcomeScreen()
        }

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username or password cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                // Check if the user is registered
                val storedPassword = sharedPreferences.getString(username, "")

                if (password == storedPassword) {
                    // If username and password match, navigate to welcome screen
                    sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                    sharedPreferences.edit().putString("username", username).apply()
                    navigateToWelcomeScreen()
                } else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        registerButton.setOnClickListener {
            // Open RegisterActivity when register button is clicked
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToWelcomeScreen() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
