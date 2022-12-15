package com.example.tp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    companion object {
        var orderlist = mutableListOf<orderSet>()//orderSet전역변수로 선언
        var list = mutableListOf<AllMenu>()
    }
    lateinit var database : DatabaseReference
    var origStock2 = ArrayList<AllMenuStock>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)
        //DB연결 확인용 코드
//        database = Firebase.database.reference
//        database.child("Test01").setValue("success!")
        var start : Int = 0
        start = intent.getIntExtra("start", 0)
        Log.d("DataCheck", "start = ${start}")
        if(start == 0){
            database = Firebase.database.reference
            var origStock = ArrayList<AllMenuStock>()
            origStock.clear()

            database.child("hamburger").get().addOnSuccessListener {
                for(item in it.children){
                    var kind : String = "hamburger"
                    var image = item.child("image").getValue().toString()
                    var name = item.child("name").getValue().toString()
                    var left = item.child("stock").getValue().toString()
                    var price = item.child("price").getValue().toString()
                    Log.d("BurgerDataCheck", "kind : ${kind}, name : ${name}, stock : ${left}")
                    //받은 데이터를 Allmenu클래스에 담기
                    //origStock.add(AllMenuStock(kind, image, name, price, left))
                }
            }

            database.child("drink").get().addOnSuccessListener {
                for(item in it.children){
                    var kind : String = "drink"
                    var image = item.child("image").getValue().toString()
                    var name = item.child("name").getValue().toString()
                    var left = item.child("stock").getValue().toString()
                    var price = item.child("price").getValue().toString()
                    Log.d("DrinkDataCheck", "kind : ${kind}, name : ${name}, stock : ${left}")
                    //받은 데이터를 Allmenu클래스에 담기
                    //origStock.add(AllMenuStock(kind, image, name, price, left))
                }
            }

            database.child("side").get().addOnSuccessListener {
                for(item in it.children){
                    var kind : String = "side"
                    var image = item.child("image").getValue().toString()
                    var name = item.child("name").getValue().toString()
                    var left = item.child("stock").getValue().toString()
                    var price = item.child("price").getValue().toString()
                    Log.d("SideDataCheck", "kind : ${kind}, name : ${name}, stock : ${left}")
                    //받은 데이터를 Allmenu클래스에 담기
                    //origStock.add(AllMenuStock(kind, image, name, price, left))
                }
            }
            origStock2 = origStock
        }
        else{
            //var origStock = intent.getSerializableExtra("origStock") as ArrayList<AllMenuStock>
            //origStock2 = origStock
        }

        val firstVisit = findViewById<Button>(R.id.main_btn1)
        firstVisit.setOnClickListener{
            val intent = Intent(this, ChoosePatty::class.java)
            //intent.putExtra("origStock", origStock2 as java.io.Serializable)
//            for(i : AllMenuStock in origStock2){
//                Log.d("origstock", "${i.kind} , ${i.name} , ${i.price} , ${i.left}")
//            }
            startActivity(intent)
        }

        val secondVisit = findViewById<Button>(R.id.main_btn2)
        secondVisit.setOnClickListener{
            val intent = Intent(this, EnterPhoneNumber::class.java)
            intent.putExtra("whereFrom", 0) // 전화번호 레이아웃 재사용 위함 | 0: 기존 고객, 1: 새로운 고객
            //intent.putExtra("origStock", origStock2 as java.io.Serializable)
            for(i : AllMenuStock in origStock2){
                Log.d("origstock", "${i.kind} , ${i.name} , ${i.price} , ${i.left}")
            }
            startActivity(intent)

        }

        val normalKiosk = findViewById<Button>(R.id.main_btn3)
        normalKiosk.setOnClickListener{
            val intent = Intent(this, ShowMenu::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
//        super.onBackPressed()
    }
}