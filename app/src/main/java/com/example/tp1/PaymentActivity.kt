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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = PaymentLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = Firebase.database.reference
        val phoneText1 = findViewById<TextView>(R.id.pay_phone_numbers1)
        val phoneText2 = findViewById<TextView>(R.id.pay_phone_numbers2)
        val whereFrom = intent.getIntExtra("whereFrom", 1)
        val numbers = intent.getStringExtra("numbers")
        val orderSet = intent.getStringExtra("orderSet")
        val orderSet2 = intent.getStringExtra("orderSet2")
        val hamOrders = intent.getStringExtra("hamOrders")
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
            intent.putExtra("whereFrom", whereFrom)
            startActivity(intent)
        }

        val mainIntent = Intent(this, MainActivity::class.java)
        var	alertDialog = AlertDialog.Builder(this)
        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                for (i in hamOrders.toString().split(",")) {
                    var splited = i.split("!")
                    database.child("hamburger").child(convertBurgerName(splited[0])).child("stock").setValue((splited[2].toInt() - splited[1].toInt()).toString())
                }
                startActivity(mainIntent)
            }
        }

        val dataName = mutableListOf<String>()
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataName.clear()
                for (i in snapshot.child("user").child(numbers.toString()).children)
                    dataName.add(i.key.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }) // 리스너 등록

        fun payMethod() {
            if (whereFrom == 0) {
                var min = 1
                for (i in 0 until dataName.size) {
                    val tmp = dataName[i].slice(IntRange(3, 3)).toInt()
                    if (min >= tmp)
                        min = tmp + 1
                }

                var hams = orderSet2.toString().split("?")[0]
                hams = hams.replace(hams.split("!")[0], convertBurgerName(hams.split("!")[0]))
                var sides = orderSet2.toString().split("?")[1]
                sides = sides.replace(sides.split("!")[0], convertSideName(sides.split("!")[0]))
                var drinks = orderSet2.toString().split("?")[2]
                drinks = drinks.replace(drinks.split("!")[0], convertDrinkName(drinks.split("!")[0]))

                val setBase = database.child("user").child(numbers.toString()).child("set" + min)
                setBase.child("hamburger").setValue(hams)
                setBase.child("side").setValue(sides)
                setBase.child("drink").setValue(drinks)
            }

            alertDialog.setTitle("Payment")
            alertDialog.setMessage("결제가 완료되었습니다!")
            alertDialog.setPositiveButton("OK", eventHandler)
            alertDialog.show()
        }
        binding.cardPay.setOnClickListener {
            payMethod()
        }
        binding.giftPay.setOnClickListener {
            payMethod()
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
            ""
    }
    fun convertDrinkName(name: String) : String {
        return if (name.equals("콜라"))
            "drink1"
        else if (name.equals("사이다"))
            "drink2"
        else if (name.equals("밀크쉐이크"))
            "drink3"
        else
            ""
    }
    fun convertSideName(name: String) : String {
        return if (name.equals("포테이토"))
            "side1"
        else if (name.equals("치즈스틱"))
            "side2"
        else if (name.equals("소프트콘"))
            "side3"
        else
            ""
    }
}