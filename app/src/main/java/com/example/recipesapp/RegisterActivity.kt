package com.example.recipesapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var registerUsernameEditText: EditText
    private lateinit var registerPasswordEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        registerUsernameEditText = findViewById(R.id.registerUsernameEditText)
        registerPasswordEditText = findViewById(R.id.registerPasswordEditText)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            val username = registerUsernameEditText.text.toString()
            val password = registerPasswordEditText.text.toString()

            // Check if username already exists
            if (sharedPreferences.contains(username)) {
                Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show()
            } else {
                // Save username and password
                sharedPreferences.edit().putString(username, password).apply()
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                finish() // Finish registration activity and go back to login
            }
        }
    }
}
