package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ChooseTaste : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choice_taste)

        val tasteSweet = findViewById<Button>(R.id.choice_taste_sweet)
        tasteSweet.setOnClickListener{
            val intent = Intent(this, ShowMenu::class.java)
            startActivity(intent)
        }
        val tasteSpicy = findViewById<Button>(R.id.choice_taste_spicy)
        tasteSpicy.setOnClickListener{
            val intent = Intent(this, ShowMenu::class.java)
            startActivity(intent)
        }
        val tastePlain = findViewById<Button>(R.id.choice_taste_plain)
        tastePlain.setOnClickListener{
            val intent = Intent(this, ShowMenu::class.java)
            startActivity(intent)
        }
    }
}