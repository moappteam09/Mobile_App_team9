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

        var hamlist = ArrayList<Hamburger>() //햄버거 객체를 저장하는 list
        var all_hamburger = Firebase.database.reference// hamburger 객체를 레퍼런스함.
//        Log.d("Hamburger", "PrintValue : ${all_hamburger}")
        all_hamburger.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                print(snapshot.children.count())
                for(ds in snapshot.children) {
                    when {
                        "hamburger".equals(ds.key) -> {
                            //val hambur = snapshot.getValue<Hamburger>()
                            val ham = snapshot.child("hamburger")
                            val hambur = snapshot.getValue<Hamburger>()
                            //Log.d("Database", "show me ${hambur.taste}")
                            //Log.d("Database","Value : ${ham.childrenCount}")
                            //hamlist.add(ham)
                            for(item in ham.children) {
                                var index : Int = item.child("_id").value as Int
                                hamlist[index].image = item.child("image").value as String
                                hamlist[index].name = item.child("name").value as String
                                hamlist[index].price = item.child("price").value as String
                                hamlist[index].patty = item.child("patty").value as String
                                hamlist[index].taste = item.child("taste").value as String
                                //hamlist[index].left = item.child("stock").value as String
                                Log.d("Database", "Values : ${item.key.toString()}")
                                //var image : String = item.child("image").value as String
//                                Log.d("Database", "Values : $image")
//                                var name : String = item.child("name").value as String
//                                Log.d("Database", "Values : $name")
//                                var price : String = item.child("price").value as String
//                                Log.d("Database", "Values : $price")
//                                var patty : String = item.child("patty").value as String
//                                Log.d("Database", "Values : $patty")
//                                var taste : String = item.child("taste").value as String
//                                Log.d("Database", "Values : $taste")
//                                var left  : String = item.child("stock").value as String
//                                Log.d("Database", "Values : $left")

                            }
                        }
                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
                print(error.message)
            }

        })


        ////////////////////////////////////////////////////////////////////////////////////////////////

        binding.adminRecyclerView.adapter = AdminList_Adapter(hamlist)

    }
}

