package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.renderscript.Sampler.Value
import android.util.Log
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

        ////////////////메뉴 보여주는 리사이클러뷰에 데이터 불러와서 넣는 과정 구현////////////////
        val hamlist = ArrayList<Hamburger>() //햄버거 객체를 저장하는 list
        val drinklist = ArrayList<Drink>() //음료 객체를 저장하는 list
        val sidelist = ArrayList<Side>() //사이드메뉴 객체를 저장하는 list
        val alllist = ArrayList<AllMenu>() //모든 메뉴의 이미지, 이름, 가격이 들어가있는 클래스의 list

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
                                //받은 데이터를 Allmenu클래스에 담기
                                alllist.add(AllMenu(image, name, price))
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
                                alllist.add(AllMenu(image, name, price))
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
                                alllist.add(AllMenu(image, name, price))
                            }
                        }
                    }
                }
                binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                binding.menuRecycler.adapter = ShowAll_Adapter(alllist)
            }
            override fun onCancelled(error: DatabaseError) {
                print(error.message)
            }

        })
        //전체 버튼을 눌렀을 경우, 햄버거와 사이드 음료 모든 메뉴를 리사이클러뷰로 보여줌
        binding.watchAll.setOnClickListener() {
            binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
            binding.menuRecycler.adapter = ShowAll_Adapter(alllist)

        }


        //햄버거 버튼을 눌렀을 경우 햄버거 메뉴를 보여줌

        binding.watchHam.setOnClickListener() {
            all_hamburger.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(ds in snapshot.children) {
                        when {
                            "hamburger".equals(ds.key) -> {
                                val ham = snapshot.child("hamburger")
                                for(item in ham.children) {
                                    var image : String = item.child("image").value as String
                                    var name : String = item.child("name").value as String
                                    var price : String = item.child("price").value as String
                                    var patty : String = item.child("patty").value as String
                                    var taste : String = item.child("taste").value as String
                                    var left  : String = item.child("stock").value as String
                                    //받은 데이터를 Hamburger클래스에 담기
                                    if(hamlist.size != ham.childrenCount.toInt()) {
                                        hamlist.add(Hamburger(image, name, price, patty, taste, left.toInt(), 0))
                                    }
                                }
                            }
                        }
                    }
                    //리사이클러뷰 어댑터에 받은 데이터 넘겨주는 부분
                    binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                    binding.menuRecycler.adapter = ShowMenuList_Adapter(hamlist)
//                (binding.menuRecycler.adapter as ShowMenuList_Adapter).notifyDataSetChanged()


                }
                override fun onCancelled(error: DatabaseError) {
                    print(error.message)
                }

            })
        }

        //음료 버튼 클릭했을 때 DB에서 데이터 받아와서 넘겨주는 부분//
        binding.watchBev.setOnClickListener() {
            all_hamburger.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
//                print(snapshot.children.count())
                    for(ds in snapshot.children) {
                        when {
                            "drink".equals(ds.key) -> {
                                val dri = snapshot.child("drink")
                                for(item in dri.children) {
                                    var image : String = item.child("image").value as String
                                    var name : String = item.child("name").value as String
                                    var price : String = item.child("price").value as String
                                    var left  : String = item.child("stock").value as String
                                    //받은 데이터를 Hamburger클래스에 담기
                                    if(drinklist.size!=dri.childrenCount.toInt()) {
                                        drinklist.add(Drink(image, name, price, 0))
                                    }

                                }
                            }
                        }
                    }
                    binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                    binding.menuRecycler.adapter = ShowDrink_Adapter(drinklist) //음료데이터 처리해주는 어댑터 따로 만들어주기
//                    (binding.menuRecycler.adapter as ShowDrink_Adapter).notifyDataSetChanged()


                }
                override fun onCancelled(error: DatabaseError) {
                    print(error.message)
                }

            })

        }

        //사이드 버튼 클릭했을 때 DB에서 데이터 받아와서 넘겨주는 부분//
        binding.watchSide.setOnClickListener() {
            all_hamburger.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
//                print(snapshot.children.count())
                    for(ds in snapshot.children) {
                        when {
                            "side".equals(ds.key) -> {
                                val side = snapshot.child("side")
                                for(item in side.children) {
                                    var image : String = item.child("image").value as String
                                    var name : String = item.child("name").value as String
                                    var price : String = item.child("price").value as String
                                    var left  : String = item.child("stock").value as String
                                    //받은 데이터를 Hamburger클래스에 담기
                                    if(sidelist.size != side.childrenCount.toInt()) {
                                        sidelist.add(Side(image, name, price, 0))
                                    }

                                }
                            }
                        }
                    }
                    binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                    binding.menuRecycler.adapter = ShowSide_Adapter(sidelist) //음료데이터 처리해주는 어댑터 따로 만들어주기
//                    (binding.menuRecycler.adapter as ShowSide_Adapter).notifyDataSetChanged()


                }
                override fun onCancelled(error: DatabaseError) {
                    print(error.message)
                }

            })

        }



        ////////////////////////////////////////////////////////////////////////////////////////////////

        val order = findViewById <Button>(R.id.btn_buy)  //주문하기 버튼
        order.setOnClickListener{
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }
    }
}