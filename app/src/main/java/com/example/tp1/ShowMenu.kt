package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.renderscript.Sampler.Value
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
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

        Log.d("SpinTest", "value : ${binding.spinPatty.selectedItem}, ${binding.spinTaste.selectedItem}")
        ////////////////메뉴 보여주는 리사이클러뷰에 데이터 불러와서 넣는 과정 구현////////////////
        val hamlist = ArrayList<Hamburger>() //햄버거 객체를 저장하는 list
        val drinklist = ArrayList<Drink>() //음료 객체를 저장하는 list
        val sidelist = ArrayList<Side>() //사이드메뉴 객체를 저장하는 list
        val alllist = ArrayList<AllMenu>() //모든 메뉴의 이미지, 이름, 가격이 들어가있는 클래스의 list
        var hamall = ArrayList<AllMenu>() //alllist에서 햄버거만 저장
        var drinkall = ArrayList<AllMenu>() //alllist에서 드링크만 저장
        var sideall = ArrayList<AllMenu>() //alllist에서 사이드만 저장

        var all_hamburger = Firebase.database.reference// 파이어베이스 DB객체를 레퍼런스함.

        //일단 DB에 있는 모든 메뉴들을 불러와서 저장해놓기 (전체 메뉴를 보여주기 위함)
        all_hamburger.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children) {
                    when {
                        "hamburger".equals(ds.key) -> {
                            val hamall = snapshot.child("hamburger")
                            Log.d("Database","Value : ${hamall.childrenCount}")
                            for(item in hamall.children) {
                                var image : String = item.child("image").value as String
                                var name : String = item.child("name").value as String
                                var price : String = item.child("price").value as String
                                var patty : String = item.child("patty").value as String
                                var taste : String = item.child("taste").value as String
                                var left : String = item.child("stock").value as String

                                //받은 데이터를 Allmenu클래스에 담기
                                alllist.add(AllMenu(image, name, price, patty, taste, left.toInt(), 0, 0))
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
                                var image : String = item.child("image").value as String
                                var name : String = item.child("name").value as String
                                var price : String = item.child("price").value as String
                                //받은 데이터를 Allmenu클래스에 담기
                                alllist.add(AllMenu(image, name, price, "", "",0, 0, 2 ))
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
                                var image : String = item.child("image").value as String
                                var name : String = item.child("name").value as String
                                var price : String = item.child("price").value as String
                                //받은 데이터를 Allmenu클래스에 담기
                                alllist.add(AllMenu(image, name, price, "", "", 0, 0, 1))
                            }
                        }
                    }
                }
                //햄버거만 저장
                for(i in 0..alllist.size-1) {
                    if(alllist[i].type == 0) {
                        hamall.add(alllist[i])
                    }
                }
                //음료만 저장
                for(i in 0..alllist.size-1) {
                    if(alllist[i].type == 2) {
                        drinkall.add(alllist[i])
                    }
                }
                //사이드만 저장
                for(i in 0..alllist.size-1) {
                    if(alllist[i].type == 1) {
                        sideall.add(alllist[i])
                    }
                }
                var afterall = ArrayList<AllMenu>()
                for(i in 0..alllist.size-1) {
                    if(alllist[i].type == 0) { //햄버거면 체크해줌
                        if(alllist[i].patty.equals(binding.spinPatty.selectedItem) && alllist[i].taste.equals(binding.spinTaste.selectedItem)) {
                            afterall.add(alllist[i])
                        }
                    }
                    else {
                        afterall.add(alllist[i])
                    }
                }
                binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                binding.menuRecycler.adapter = ShowAll_Adapter(afterall, -1)
            }
            override fun onCancelled(error: DatabaseError) {
                print(error.message)
            }

        })
        //전체 버튼을 눌렀을 경우, 햄버거와 사이드 음료 모든 메뉴를 리사이클러뷰로 보여줌
        binding.watchAll.setOnClickListener() {
            binding.spinPatty.visibility = View.VISIBLE
            binding.spinTaste.visibility = View.VISIBLE
            var afterall = ArrayList<AllMenu>()
            for(i in 0..alllist.size-1) {
                if(alllist[i].type == 0) { //햄버거면 체크해줌
                    if(alllist[i].patty.equals(binding.spinPatty.selectedItem) && alllist[i].taste.equals(binding.spinTaste.selectedItem)) {
                        afterall.add(alllist[i])
                    }
                }
                else {
                    afterall.add(alllist[i])
                }
            }
            binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
            binding.menuRecycler.adapter = ShowAll_Adapter(afterall, -1)
        }

        //햄버거 버튼을 눌렀을 경우 햄버거 메뉴를 보여줌
        binding.watchHam.setOnClickListener() {
            binding.spinPatty.visibility = View.VISIBLE
            binding.spinTaste.visibility = View.VISIBLE
            var afterall = ArrayList<AllMenu>()
            for(i in 0..hamall.size-1) {
                if(alllist[i].patty.equals(binding.spinPatty.selectedItem) && alllist[i].taste.equals(binding.spinTaste.selectedItem)) {
                    afterall.add(hamall[i])
                }
            }
            binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
            binding.menuRecycler.adapter = ShowAll_Adapter(afterall, 0)
        }

        //음료 버튼 클릭했을 때 DB에서 데이터 받아와서 넘겨주는 부분//
        binding.watchBev.setOnClickListener() {
            binding.spinPatty.visibility = View.INVISIBLE
            binding.spinTaste.visibility = View.INVISIBLE
            binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
            binding.menuRecycler.adapter = ShowAll_Adapter(drinkall, 2)
        }

        //사이드 버튼 클릭했을 때 DB에서 데이터 받아와서 넘겨주는 부분//
        binding.watchSide.setOnClickListener() {
            binding.spinPatty.visibility = View.INVISIBLE
            binding.spinTaste.visibility = View.INVISIBLE
            binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
            binding.menuRecycler.adapter = ShowAll_Adapter(sideall, 1)

        }



        ////////////////////////////////////////////////////////////////////////////////////////////////

        val order = findViewById <Button>(R.id.btn_buy)  //주문하기 버튼
        order.setOnClickListener{
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }
    }
}