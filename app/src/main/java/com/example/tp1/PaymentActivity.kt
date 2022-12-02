package com.example.tp1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tp1.databinding.PaymentLayoutBinding


class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = PaymentLayoutBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}