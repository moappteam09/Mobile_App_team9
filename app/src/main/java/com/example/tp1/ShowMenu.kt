package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.renderscript.Sampler.Value
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tp1.databinding.SelectMenuBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

//메뉴를 선택하는 화면을 보여주는 클래스
//spinner구현해야함

class ShowMenu  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val binding = SelectMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var inform_ham = intent.getStringExtra("Patty_and_Taste")
        if (inform_ham.isNullOrEmpty())
            inform_ham = "0 0"
        var patty = inform_ham?.slice(IntRange(0,0))
        var taste = inform_ham?.slice(IntRange(2,2))

        var cnt = 0 //burger의 item 번호를 알려주기위한 변수 선언

        Log.d("Data", "${inform_ham}")
        //스피너 태그 id로 변수에 저장해주기

        val spinner_taste = findViewById<Spinner>(R.id.spin_taste)
        val spinner_patty = findViewById<Spinner>(R.id.spin_patty)

        //스피너 어댑터에 연결해주기
        spinner_taste.adapter = ArrayAdapter.createFromResource(this, R.array.taste, android.R.layout.simple_spinner_item)
        binding.spinTaste.setSelection(taste!!.toInt())
        spinner_patty.adapter = ArrayAdapter.createFromResource(this, R.array.patty, android.R.layout.simple_spinner_item)
        binding.spinPatty.setSelection(patty!!.toInt())

        ////////////////메뉴 보여주는 리사이클러뷰에 데이터 불러와서 넣는 과정 구현////////////////

        var hamlist = ArrayList<Hamberger>() //햄버거 객체를 저장하는 list
        var all_hamburger = Firebase.database.reference// hamburger 객체를 레퍼런스함.
//        Log.d("Hamburger", "PrintValue : ${all_hamburger}")
        all_hamburger.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                print(snapshot.children.count())
                for(ds in snapshot.children) {
                    when {
                        "hamburger".equals(ds.key) -> {
                            val ham = snapshot.child("hamburger")
                            Log.d("Database","Value : ${ham.childrenCount}")
                            for(item in ham.children) {
                                Log.d("Database", "Values : ${item.key.toString()}")
                                var image : String = item.child("image").value as String
                                Log.d("Database", "Values : $image")
                                var name : String = item.child("name").value as String
                                Log.d("Database", "Values : $name")
                                var price : String = item.child("price").value as String
                                Log.d("Database", "Values : $price")
                                var patty : String = item.child("patty").value as String
                                Log.d("Database", "Values : $patty")
                                var taste : String = item.child("taste").value as String
                                Log.d("Database", "Values : $taste")
                                var left  : String = item.child("stock").value as String
                                Log.d("Database", "Values : $left")

                            }
                        }
                    }
//                    cnt++
//                    var key = "burger" + "${cnt.toString()}"
//                    Log.d("Burger", "Hamburger`s children : ${ds}")
//                    Log.d("Burger", "Hamburger`s children : ${ds.child("burger1").getValue()}")
//                    val ham = snapshot.child("$key") //burger1, burger2, .. 등의 객체를 가져옴
//                    for(item in ham.children) {
//                        var image : String = item.child("image").value as String
//                        var name : String = item.child("name").value as String
//                        var price : String = item.child("price").value as String
//                        var patty : String = item.child("patty").value as String
//                        var taste : String = item.child("taste").value as String
//                        var left  : Int = item.child("stock").value as Int
//
//                        val hamClass = Hamberger(image, name, price, patty, taste, left)
//                        hamlist.add(hamClass)
//                        Log.d("HamClass", "HamList : ${hamlist}")
//                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                print(error.message)
            }

        })
//
//        binding.menuRecycler.layoutManager = LinearLayoutManager(this)
//        binding.menuRecycler.adapter = ShowMenuList_Adapter(hamlist)
//
//        binding.menuRecycler.addItemDecoration(
//            DividerItemDecoration(this,
//            LinearLayoutManager.VERTICAL))

        ////////////////////////////////////////////////////////////////////////////////////////////////

        val order = findViewById <Button>(R.id.btn_buy)  //주문하기 버튼
        order.setOnClickListener{
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }
    }
}

