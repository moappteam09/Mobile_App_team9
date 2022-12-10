package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp1.databinding.CheckListBinding
import com.example.tp1.databinding.ItemLayoutBinding
import com.example.tp1.databinding.SelectMenuBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CheckSelectedMenu : AppCompatActivity() {
    lateinit var database: DatabaseReference
    var numbers: String? = ""

    var dataSet = mutableListOf(orderSet())

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val binding = CheckListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Firebase.database.reference
        dataSet.clear()

        class MyViewHolder(val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root)
        class MyAdapter(val dataSet: MutableList<orderSet>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return MyViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                Log.d("RecyclerView", "onBindViewHolder(): $position")
                val binding	= (holder as MyViewHolder).binding
                binding.itemName.text =	dataSet[position].ham.name + ", " + dataSet[position].drink.name + ", " +dataSet[position].side.name // 뷰에 데이터 출력
                binding.itemBtn.setOnClickListener { // 뷰에 이벤트 추가
                    val intent = Intent(this@CheckSelectedMenu, PaymentActivity::class.java)
                    intent.putExtra("numbers", numbers)
                    startActivity(intent)
                }
            }
            override fun getItemCount(): Int {
                return dataSet.size
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = MyAdapter(dataSet)
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataSet.clear()
                var data = snapshot.child("user").children
                for (i in data) {
                    var pNum = i.child("phone").getValue().toString()
                    var str = pNum.slice(IntRange(4, 7)) + pNum.slice(IntRange(9, 12))
                    if (str.equals(numbers)) {
                        for (j in i.child("setMenu").children) {
                            Log.d("Debug", j.child("hamburger").getValue().toString())
                            val ham = Hamberger("url", j.child("hamburger").getValue().toString(), "1000", "cow", "sweet", 1)
                            val drink = Drink("url", j.child("drink").getValue().toString(), "1000")
                            val side = Side("url", j.child("side").getValue().toString(), "1000")
                            dataSet.add(orderSet(ham, drink, side))
                        }
                    }
                }
                (binding.recyclerView.adapter as MyAdapter).notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }) // 리스너 등록

        numbers = intent.getStringExtra("numbers")
        val orderbtn = findViewById<Button>(R.id.check_list_orderbtn)
        orderbtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}