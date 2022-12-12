package com.example.tp1

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.tp1.databinding.ItemLayoutBinding
import com.example.tp1.databinding.PaymentLayoutBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = PaymentLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val phoneText1 = findViewById<TextView>(R.id.pay_phone_numbers1)
        val phoneText2 = findViewById<TextView>(R.id.pay_phone_numbers2)
        val numbers = intent.getStringExtra("numbers")
        var orderSet = intent.getStringExtra("orderSet")
        var hamOrders = intent.getStringExtra("hamOrders")
        val totalPrice = intent.getIntExtra("totalPrice", 0)

        binding.totalOrder.setMovementMethod(ScrollingMovementMethod())
        if (orderSet != null) {
            for (i in orderSet.split(","))
                binding.totalOrder.setText(binding.totalOrder.text.toString() + i.split("|")[0].split("!")[0]
                        + " x" + i.split("|")[0].split("!")[1] + " : " + i.split("|")[1] + "원" + "\n")
        }
        binding.totalPrice.setText("합계 : " + totalPrice.toString() + "원")
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

        val mainIntent = Intent(this, MainActivity::class.java)
        var	alertDialog = AlertDialog.Builder(this)
        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                val database = Firebase.database.reference
                for (i in hamOrders.toString().split(",")) {
                    var splited = i.split("!")
                    database.child("hamburger").child(convertBurgerName(splited[0])).child("stock").setValue((splited[2].toInt() - splited[1].toInt()).toString())
                }
                startActivity(mainIntent)
            }
        }
        binding.cardPay.setOnClickListener {
            alertDialog.setTitle("Payment")
            alertDialog.setMessage("결제가 완료되었습니다!")
            alertDialog.setPositiveButton("OK",eventHandler)
            alertDialog.show()
        }
        binding.giftPay.setOnClickListener {
            alertDialog.setTitle("Payment")
            alertDialog.setMessage("결제가 완료되었습니다!")
            alertDialog.setPositiveButton("OK",eventHandler)
            alertDialog.show()
        }
    }
    fun convertBurgerName(name: String) : String {
        return if (name.equals("불고기버거"))
            "burger1"
        else if (name.equals("치킨버거"))
            "burger2"
        else if (name.equals("리아미라클버거"))
            "burger3"
        else if (name.equals("베이컨 햄에그번"))
            "burger4"
        else
            "undefined"
    }
}