package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ChoosePatty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choice_patty)

        val pattyCow = findViewById<Button>(R.id.choice_patty_cow)
        pattyCow.setOnClickListener{
            val intent = Intent(this, ChooseTaste::class.java)
            startActivity(intent)
        }
        val pattyPig = findViewById<Button>(R.id.choice_patty_pig)
        pattyPig.setOnClickListener{
            val intent = Intent(this, ChooseTaste::class.java)
            startActivity(intent)
        }
        val pattyChicken = findViewById<Button>(R.id.choice_patty_chicken)
        pattyChicken.setOnClickListener{
            val intent = Intent(this, ChooseTaste::class.java)
            startActivity(intent)
        }
        val pattySubstitute = findViewById<Button>(R.id.choice_patty_substitute)
        pattySubstitute.setOnClickListener{
            val intent = Intent(this, ChooseTaste::class.java)
            startActivity(intent)
        }
    }
}