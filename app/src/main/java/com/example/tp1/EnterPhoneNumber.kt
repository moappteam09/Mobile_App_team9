package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EnterPhoneNumber : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.phone_number)

        val whereFrom = intent.getIntExtra("whereFrom", 0)
        val orderSet = intent.getStringExtra("orderSet")
        val orderSet2 = intent.getStringExtra("orderSet2")
        val orderSet3 = intent.getStringExtra("orderSet3")
        val totalPrice = intent.getIntExtra("totalPrice", 0)
        val hamOrders = intent.getStringExtra("hamOrders")
        //val origStock = intent.getSerializableExtra("origStock") as ArrayList<AllMenuStock>
//        for(i : AllMenuStock in origStock){
//            Log.d("origstock", "${i.name}")
//        }


        var numbers: String = ""
        val numView1: TextView = findViewById<TextView>(R.id.phone_number_numbers2)
        val numView2: TextView = findViewById<TextView>(R.id.phone_number_numbers3)

        var btns = mutableListOf<Button>()
        btns.add(findViewById<Button>(R.id.phone_number_btns_btn0))
        btns.add(findViewById<Button>(R.id.phone_number_btns_btn1))
        btns.add(findViewById<Button>(R.id.phone_number_btns_btn2))
        btns.add(findViewById<Button>(R.id.phone_number_btns_btn3))
        btns.add(findViewById<Button>(R.id.phone_number_btns_btn4))
        btns.add(findViewById<Button>(R.id.phone_number_btns_btn5))
        btns.add(findViewById<Button>(R.id.phone_number_btns_btn6))
        btns.add(findViewById<Button>(R.id.phone_number_btns_btn7))
        btns.add(findViewById<Button>(R.id.phone_number_btns_btn8))
        btns.add(findViewById<Button>(R.id.phone_number_btns_btn9))
        btns.add(findViewById<Button>(R.id.phone_number_btns_del))
        fun checkDone() {
            lateinit var intent: Intent
            val length = numbers.length
            if (length == 8) {
                if (whereFrom == 0)
                    intent = Intent(this, CheckSelectedMenu::class.java)
                else {
                    intent = Intent(this, PaymentActivity::class.java)
                    intent.putExtra("whereFrom", whereFrom)
                    intent.putExtra("orderSet", orderSet)
                    intent.putExtra("orderSet2", orderSet2)
                    intent.putExtra("orderSet3", orderSet3)
                    intent.putExtra("totalPrice", totalPrice)
                    intent.putExtra("hamOrders", hamOrders)
                }
                intent.putExtra("numbers", numbers)
                //intent.putExtra("origStock", origStock as java.io.Serializable)
                startActivity(intent)
            }
        }
        fun printNum() {
            val length = numbers.length
            if (length > 4) {
                numView1.setText(numbers.slice(IntRange(0, 3)))
                numView2.setText(numbers.slice(IntRange(4, length - 1)))
            } else {
                numView1.setText(numbers.slice(IntRange(0, length - 1)))
                numView2.setText("")
            }
        }
        for (i in 0..9)
            btns[i].setOnClickListener{
                val length = numbers.length
                if (length < 8)
                    numbers += i.toString()
                printNum()
                checkDone()
            }
        var stack = 0
        btns[10].setOnClickListener{
            val length = numbers.length
            if (length == 0)
                stack++
            else
                stack = 0
            if (length > 0)
                numbers = numbers.slice(IntRange(0, numbers.length - 2))
            if (stack > 4) {
                val intent = Intent(this, AdminList::class.java)
                //intent.putExtra("origStock", origStock as java.io.Serializable)
                startActivity(intent)
            }
            printNum()
        }
    }
    override fun onBackPressed() {
//        super.onBackPressed()
    }
}