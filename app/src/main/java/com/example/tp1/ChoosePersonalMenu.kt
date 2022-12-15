package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ChoosePersonalMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choice_menu)

        val menuPersonal = findViewById<Button>(R.id.choice_menu_personal)
        menuPersonal.setOnClickListener{
            val intent = Intent(this, EnterPhoneNumber::class.java)
            startActivity(intent)
        }
        val menuNew = findViewById<Button>(R.id.choice_menu_new)
        menuNew.setOnClickListener{
            val intent = Intent(this, ChoosePatty::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
//        super.onBackPressed()
    }
}