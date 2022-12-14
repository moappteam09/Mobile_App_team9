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
import com.bumptech.glide.Glide
import com.example.tp1.databinding.CheckListBinding
import com.example.tp1.databinding.ItemLayoutBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CheckSelectedMenu : AppCompatActivity() {
    lateinit var database: DatabaseReference
    var numbers: String? = ""

    var dataSet = mutableListOf<orderSet>()
    var dataName = mutableListOf<String>()

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
                val binding	= (holder as MyViewHolder).binding

                Glide.with(holder.itemView)
                    .load(dataSet[position].getRepresentedImage()) // 불러올 이미지 url
                    .into(binding.itemImg) // 이미지를 넣을 뷰

                binding.itemName.text =	dataSet[position].getFullName()
                binding.itemPrice.text = dataSet[position].getTotalPrice().toString()
                binding.delBtn.setOnClickListener { // 뷰에 이벤트 추가
                    database.child("user").child(numbers.toString()).child(dataName[position]).setValue(null)
                    notifyDataSetChanged()
                }
                binding.root.setOnClickListener { // 뷰에 이벤트 추가
                    val intent = Intent(this@CheckSelectedMenu, PaymentActivity::class.java)
                    intent.putExtra("whereFrom", 0)
                    intent.putExtra("numbers", numbers)
                    intent.putExtra("orderSet", dataSet[position].getAll())
                    intent.putExtra("orderSet2", dataSet[position].getAllByDivision())
                    intent.putExtra("orderSet3", dataSet[position].getAllByDivisionPlusPrice())
                    intent.putExtra("totalPrice", dataSet[position].getTotalPrice())
                    intent.putExtra("hamOrders", dataSet[position].getHamburger())
                    //intent.putExtra("origStock", origStock as java.io.Serializable)
                    startActivity(intent)
                    database.onDisconnect()
                    finish()
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
                dataName.clear()
                var data = snapshot.child("user").children
                for (i in data) {
                    for (l in i.children) {
                        if (i.key.equals(numbers)) {
                            var hamData = snapshot.child("hamburger")
                            var drinkData = snapshot.child("drink")
                            var sideData = snapshot.child("side")
                            var hams = mutableListOf<Hamburger>()
                            var drinks = mutableListOf<Drink>()
                            var sides = mutableListOf<Side>()
                            dataName.add(l.key.toString())
                            fun hamAdd(k: DataSnapshot, orderCount : Int) {
                                hams.add(
                                    Hamburger(
                                        k.child("image").getValue().toString(),
                                        k.child("name").getValue().toString(),
                                        k.child("price").getValue().toString(),
                                        k.child("patty").getValue().toString(),
                                        k.child("taste").getValue().toString(),
                                        k.child("stock").getValue().toString().toInt(),
                                        orderCount
                                    )
                                )
                            }

                            fun drinkAdd(k: DataSnapshot, orderCount : Int) {
                                drinks.add(
                                    Drink(
                                        k.child("image").getValue().toString(),
                                        k.child("name").getValue().toString(),
                                        k.child("price").getValue().toString(),
                                        orderCount
                                    )
                                )
                            }

                            fun sideAdd(k: DataSnapshot, orderCount : Int) {
                                sides.add(
                                    Side(
                                        k.child("image").getValue().toString(),
                                        k.child("name").getValue().toString(),
                                        k.child("price").getValue().toString(),
                                        orderCount
                                    )
                                )
                            }

                            if (l.child("hamburger").getValue() != null
                                && l.child("drink").getValue() != null
                                && l.child("side").getValue() != null) {
                                var splitString = l.child("hamburger").getValue().toString().split(",")
                                if (l.child("hamburger").getValue().toString().isNotEmpty())
                                    for (j in splitString)
                                        hamAdd(
                                            hamData.child(j.split("!")[0]),
                                            j.split("!")[1].toInt()
                                        )
                                splitString = l.child("drink").getValue().toString().split(",")
                                if (l.child("drink").getValue().toString().isNotEmpty())
                                    for (j in splitString)
                                        drinkAdd(
                                            drinkData.child(j.split("!")[0]),
                                            j.split("!")[1].toInt()
                                        )
                                splitString = l.child("side").getValue().toString().split(",")
                                if (l.child("side").getValue().toString().isNotEmpty())
                                    for (j in splitString)
                                        sideAdd(
                                            sideData.child(j.split("!")[0]),
                                            j.split("!")[1].toInt()
                                        )
                                dataSet.add(orderSet(hams, drinks, sides))
                            }
                        }
                    }
                }
                (binding.recyclerView.adapter as MyAdapter).notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }) // 리스너 등록

        numbers = intent.getStringExtra("numbers")
        val orderBtn = binding.checkListOrderbtn
        orderBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            //intent.putExtra("origStock", origStock as java.io.Serializable)
            startActivity(intent)
        }
    }
}