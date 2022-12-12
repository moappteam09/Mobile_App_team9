package com.example.tp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)
        //DB연결 확인용 코드
//        database = Firebase.database.reference
//        database.child("Test01").setValue("success!")

        val firstVisit = findViewById<Button>(R.id.main_btn1)
        firstVisit.setOnClickListener{
            val intent = Intent(this, ChoosePatty::class.java)
            startActivity(intent)
        }

        val secondVisit = findViewById<Button>(R.id.main_btn2)
        secondVisit.setOnClickListener{
            val intent = Intent(this, EnterPhoneNumber::class.java)
            intent.putExtra("whereFrom", 0) // 전화번호 레이아웃 재사용 위함 | 0: 기존 고객, 1: 새로운 고객
            startActivity(intent)
        }

        val normalKiosk = findViewById<Button>(R.id.main_btn3)
        normalKiosk.setOnClickListener{
            val intent = Intent(this, ShowMenu::class.java)
            startActivity(intent)
        }
    }
}