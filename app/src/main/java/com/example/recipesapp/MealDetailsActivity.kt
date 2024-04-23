package com.example.recipesapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class MealDetailsActivity : AppCompatActivity() {

    private lateinit var mealNameTextView: TextView
    private lateinit var categoryTextView: TextView
    private lateinit var areaTextView: TextView
    private lateinit var instructionsTextView: TextView
    private lateinit var mealImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Find and reference views by their IDs
        mealNameTextView = findViewById(R.id.mealNameTextView)
        categoryTextView = findViewById(R.id.categoryTextView)
        areaTextView = findViewById(R.id.areaTextView)
        instructionsTextView = findViewById(R.id.instructionsTextView)
        mealImageView = findViewById(R.id.mealImageView)

        // Retrieve the meal object from the intent
        val meal = intent.getParcelableExtra<Meal>("meal")

        // Display meal details
        meal?.let {
            mealNameTextView.text = it.strMeal
            categoryTextView.text = it.strCategory
            areaTextView.text = it.strArea
            instructionsTextView.text = it.strInstructions

            // Load meal image using Picasso library
            Picasso.get().load(it.strMealThumb).into(mealImageView)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true

    }
}


