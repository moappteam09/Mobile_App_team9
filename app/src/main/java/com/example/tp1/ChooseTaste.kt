package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ChooseTaste : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choice_taste)
        var patty_and_taste = intent.getStringExtra("Patty") //이전에 선택한 패티의 정보를 받아옴

        //val origStock = intent.getSerializableExtra("origStock") as ArrayList<AllMenuStock>

        val tasteSweet = findViewById<Button>(R.id.choice_taste_sweet)
        tasteSweet.setOnClickListener{
            val intent = Intent(this, ShowMenu::class.java)
            intent.putExtra("Patty_and_Taste", "${patty_and_taste}" +" " +  "1")
            //intent.putExtra("origStock", origStock as java.io.Serializable)
            startActivity(intent)
        }
        val tasteSpicy = findViewById<Button>(R.id.choice_taste_spicy)
        tasteSpicy.setOnClickListener{
            val intent = Intent(this, ShowMenu::class.java)
            intent.putExtra("Patty_and_Taste", "${patty_and_taste}" +" " +  "2")
            //intent.putExtra("origStock", origStock as java.io.Serializable)
            startActivity(intent)
        }
        val tastePlain = findViewById<Button>(R.id.choice_taste_plain)
        tastePlain.setOnClickListener{
            val intent = Intent(this, ShowMenu::class.java)
            intent.putExtra("Patty_and_Taste", "${patty_and_taste}" +" " +  "3")
            //intent.putExtra("origStock", origStock as java.io.Serializable)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
//        super.onBackPressed()
    }
}