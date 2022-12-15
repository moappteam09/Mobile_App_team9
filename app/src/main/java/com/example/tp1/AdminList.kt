package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.renderscript.Sampler.Value
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tp1.databinding.AdminBinding
import com.example.tp1.databinding.SelectMenuBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

//메뉴를 선택하는 화면을 보여주는 클래스
//spinner구현해야함

class AdminList  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val binding = AdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ////////////////메뉴 보여주는 리사이클러뷰에 데이터 불러와서 넣는 과정 구현////////////////
        val alllist = ArrayList<AllMenuStock>() //모든 메뉴의 이미지, 이름, 가격이 들어가있는 클래스의 list
        var all_hamburger = Firebase.database.reference// 파이어베이스 DB객체를 레퍼런스함.
        binding.adminRecyclerView.layoutManager = LinearLayoutManager(this@AdminList)
        binding.adminRecyclerView.adapter = AdminList_Adapter(alllist)

        //val origStock = intent.getSerializableExtra("origStock") as ArrayList<AllMenuStock>
//        for(i : AllMenuStock in origStock){
//            Log.d("origstock", "${i.kind} , ${i.name} , ${i.price} , ${i.left}")
//        }
        var burgerSold : Int = 0
        var drinkSold : Int = 0
        var sideSold : Int = 0
        //일단 DB에 있는 모든 메뉴들을 불러와서 저장해놓기 (전체 메뉴를 보여주기 위함)
        all_hamburger.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                alllist.clear()
                Log.d("wtf", "total count: ${snapshot.childrenCount}")
                for(ds in snapshot.children) {
                    when {
                        "hamburger".equals(ds.key) -> {
                            val hamall = snapshot.child("hamburger")
                            Log.d("Database","Value : ${hamall.childrenCount}")
                            for(item in hamall.children) {
                                var kind : String = "hamburger"
                                var image = item.child("image").getValue().toString()
                                var name = item.child("name").getValue().toString()
                                var left = item.child("stock").getValue().toString()
                                var price = item.child("price").getValue().toString()
                                //받은 데이터를 Allmenu클래스에 담기
                                alllist.add(AllMenuStock(kind, image, name, price, left))
                                //Log.d("매출", "계산 결과 ${name} : ${calcSoldQuantity(kind, left, name, origStock)}")
                                //Log.d("매출", "계산 결과 ${name} : ${(calcSoldQuantity(kind, left, name, origStock) * price.toInt())}")
                                Log.d("매출", "burgersold before ${burgerSold}")
                                //burgerSold += (calcSoldQuantity(kind, left, name, origStock) * price.toInt())
                                Log.d("매출", "burgersold after ${burgerSold}")
                            }
                        }
                    }
                }
                for(ds in snapshot.children) {
                    when {
                        "drink".equals(ds.key) -> {
                            val driall = snapshot.child("drink")
                            Log.d("Database","Value : ${driall.childrenCount}")
                            for(item in driall.children) {
                                var kind : String = "drink"
                                var image = item.child("image").getValue().toString()
                                var name = item.child("name").getValue().toString()
                                var left = item.child("stock").getValue().toString()
                                var price = item.child("price").getValue().toString()
                                //받은 데이터를 Allmenu클래스에 담기
                                alllist.add(AllMenuStock(kind, image, name, price, left))
                                //drinkSold += (calcSoldQuantity(kind, left, name, origStock) * price.toInt())
                            }
                        }
                    }
                }
                for(ds in snapshot.children) {
                    when {
                        "side".equals(ds.key) -> {
                            val sideall = snapshot.child("side")
                            Log.d("Database","Value : ${sideall.childrenCount}")
                            for(item in sideall.children) {
                                var kind : String = "side"
                                var image = item.child("image").getValue().toString()
                                var name = item.child("name").getValue().toString()
                                var left = item.child("stock").getValue().toString()
                                var price = item.child("price").getValue().toString()
                                //받은 데이터를 Allmenu클래스에 담기
                                alllist.add(AllMenuStock(kind, image, name, price, left))
                                //sideSold += (calcSoldQuantity(kind, left, name, origStock) * price.toInt())
                            }
                        }
                    }
                }
                (binding.adminRecyclerView.adapter as AdminList_Adapter).notifyDataSetChanged()
                val salesText = findViewById<TextView>(R.id.salesText)
                Log.d("최종", "burgersold before ${burgerSold}")
                burgerSold = snapshot.child("sales").child("hamburger").getValue().toString().toInt()
                sideSold = snapshot.child("sales").child("side").getValue().toString().toInt()
                drinkSold = snapshot.child("sales").child("drink").getValue().toString().toInt()
                val total : Int = burgerSold + drinkSold + sideSold
                salesText.text = "햄버거: " + burgerSold.toString() +
                        "원 | 사이드: " + sideSold.toString() +
                        "원\n드링크: " + drinkSold.toString() +
                        "원 ||| 전체: " + total.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                print(error.message)
            }

        })

        var confrimBtn = findViewById<Button>(R.id.admin_confirm)
        confrimBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        var stockResetBtn = findViewById<Button>(R.id.admin_stock_reset)
        stockResetBtn.setOnClickListener{
            var reset = 0
            all_hamburger.child("sales").child("hamburger").setValue(reset)
            all_hamburger.child("sales").child("drink").setValue(reset)
            all_hamburger.child("sales").child("side").setValue(reset)

        }

    }
    override fun onBackPressed() {
//        super.onBackPressed()
    }

    fun calcSoldQuantity(kind: String, left: String, name: String, origStock: ArrayList<AllMenuStock>): Int {
//        for(i : AllMenuStock in origStock){
//            Log.d("origstock", "${i.kind} , ${i.name} , ${i.price} , ${i.left}")
//        }
//        for(i : AllMenuStock in origStock){
//            //Log.d("매출", "어디 찾아봅시다 ${i.kind} , ${kind} , ${i.name}, ${name}")
//            if(i.kind == kind && i.name == name){
//                Log.d("매출", "if문 안으로 들어왔어요${i.kind} , ${kind} , ${i.name}, ${name}, ${i.left}, ${left}")
//                return i.left.toInt() - left.toInt()
//            }
//        }
        return 0
    }
}

