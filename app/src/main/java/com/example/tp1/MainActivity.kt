package com.example.tp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)

        val firstVisit = findViewById<Button>(R.id.main_btn1)
        firstVisit.setOnClickListener{
            val intent = Intent(this, ChoosePatty::class.java)
            startActivity(intent)
        }

        val secondVisit = findViewById<Button>(R.id.main_btn2)
        secondVisit.setOnClickListener{
            val intent = Intent(this, EnterPhoneNumber::class.java)
            startActivity(intent)
        }
        val normalKiosk = findViewById<Button>(R.id.main_btn3)
        normalKiosk.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}