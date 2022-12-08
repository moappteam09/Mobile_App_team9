package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tp1.databinding.PaymentLayoutBinding


class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = PaymentLayoutBinding.inflate(layoutInflater)
        setContentView(R.layout.payment_layout)

        val phoneText1 = findViewById<TextView>(R.id.pay_phone_numbers1)
        val phoneText2 = findViewById<TextView>(R.id.pay_phone_numbers2)
        val numbers = intent.getStringExtra("numbers")

        if (!numbers.isNullOrEmpty()) {
            phoneText1.setText(numbers.slice(IntRange(0, 3)))
            phoneText2.setText(numbers.slice(IntRange(4, 7)))
        }

        val putPhoneNumber = findViewById<Button>(R.id.putPhoneNumber)
        putPhoneNumber.setOnClickListener{
            val intent = Intent(this, EnterPhoneNumber::class.java)
            intent.putExtra("whereFrom", 1)
            startActivity(intent)
        }
    }
}