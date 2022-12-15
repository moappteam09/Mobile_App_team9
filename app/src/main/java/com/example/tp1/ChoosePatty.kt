package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ChoosePatty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choice_patty)

        //val origStock = intent.getSerializableExtra("origStock") as ArrayList<AllMenuStock>

        val pattyCow = findViewById<Button>(R.id.choice_patty_cow)
        pattyCow.setOnClickListener{
            val intent = Intent(this, ChooseTaste::class.java)
            intent.putExtra("Patty", "1")
            //intent.putExtra("origStock", origStock as java.io.Serializable)
            startActivity(intent)
        }
        val pattyPig = findViewById<Button>(R.id.choice_patty_pig)
        pattyPig.setOnClickListener{
            val intent = Intent(this, ChooseTaste::class.java)
            intent.putExtra("Patty", "2")
            //intent.putExtra("origStock", origStock as java.io.Serializable)
            startActivity(intent)
        }
        val pattyChicken = findViewById<Button>(R.id.choice_patty_chicken)
        pattyChicken.setOnClickListener{
            val intent = Intent(this, ChooseTaste::class.java)
            intent.putExtra("Patty", "3")
            //intent.putExtra("origStock", origStock as java.io.Serializable)
            startActivity(intent)
        }
        val pattySubstitute = findViewById<Button>(R.id.choice_patty_substitute)
        pattySubstitute.setOnClickListener{
            val intent = Intent(this, ChooseTaste::class.java)
            intent.putExtra("Patty", "4")
            //intent.putExtra("origStock", origStock as java.io.Serializable)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
//        super.onBackPressed()
    }
}