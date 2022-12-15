package com.example.tp1

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tp1.databinding.AdminBinding
import com.example.tp1.databinding.ItemLayoutStockBinding
import com.example.tp1.databinding.MenuViewholderBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

val hamsName = mutableListOf<String>()
val hamsKey = mutableListOf<String>()
val sidesName = mutableListOf<String>()
val sidesKey = mutableListOf<String>()
val drinksName = mutableListOf<String>()
val drinksKey = mutableListOf<String>()

class AdminList_ViewHolder (val binding : ItemLayoutStockBinding) : RecyclerView.ViewHolder(binding.root)

class AdminList_Adapter(val dataSet: MutableList<AllMenuStock>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AdminList_ViewHolder(ItemLayoutStockBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("RecyclerView", "onBindViewHolder() : ${position}")
        val binding = (holder as AdminList_ViewHolder).binding
        //id값들
        //메뉴 이미지 : stock_img
        //메뉴 이름 : stock_menu_name
        //메뉴 재고 : stock_current_stock

        val database = Firebase.database.reference
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                hamsName.clear()
                hamsKey.clear()
                sidesName.clear()
                sidesKey.clear()
                drinksName.clear()
                drinksKey.clear()
                for (i in snapshot.child("hamburger").children) {
                    hamsName.add(i.child("name").getValue().toString())
                    hamsKey.add(i.key.toString())
                    Log.d("debug", i.key.toString() + " " + i.child("name").getValue().toString())
                }
                for (i in snapshot.child("side").children) {
                    sidesName.add(i.child("name").getValue().toString())
                    sidesKey.add(i.key.toString())
                    Log.d("debug", i.key.toString() + " " + i.child("name").getValue().toString())
                }
                for (i in snapshot.child("drink").children) {
                    drinksName.add(i.child("name").getValue().toString())
                    drinksKey.add(i.key.toString())
                    Log.d("debug", i.key.toString() + " " + i.child("name").getValue().toString())
                }
                hamsName.add("")
                hamsKey.add("")
                sidesName.add("")
                sidesKey.add("")
                drinksName.add("")
                drinksKey.add("")
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })


        Glide.with(holder.itemView)
            .load(dataSet[position].image) // 불러올 이미지 url
            .into(binding.stockImg) // 이미지를 넣을 뷰
        binding.stockMenuName.text = "${dataSet[position].name}"
        binding.stockCurrentStock.text = "${dataSet[position].left}"
        var originalStock = dataSet[position].left.toInt()
        Log.d("please", "original stock1: ${originalStock}")
        binding.stockInc.setOnClickListener{
            val database = Firebase.database.reference
            originalStock += 1
            if (dataSet[position].kind.equals("hamburger"))
                database.child("${dataSet[holder.adapterPosition].kind}").child(convertBurgerName("${dataSet[holder.adapterPosition].name}")).child("stock").setValue(originalStock.toString())
            else if (dataSet[position].kind.equals("side"))
                database.child("${dataSet[holder.adapterPosition].kind}").child(convertSideName("${dataSet[holder.adapterPosition].name}")).child("stock").setValue(originalStock.toString())
            else
                database.child("${dataSet[holder.adapterPosition].kind}").child(convertDrinkName("${dataSet[holder.adapterPosition].name}")).child("stock").setValue(originalStock.toString())
            notifyDataSetChanged()
        }
        binding.stockDec.setOnClickListener{
            val database = Firebase.database.reference
            if (originalStock > 0) {
                originalStock -= 1
                if (dataSet[position].kind.equals("hamburger"))
                    database.child("${dataSet[holder.adapterPosition].kind}").child(convertBurgerName("${dataSet[holder.adapterPosition].name}")).child("stock").setValue(originalStock.toString())
                else if (dataSet[position].kind.equals("side"))
                    database.child("${dataSet[holder.adapterPosition].kind}").child(convertSideName("${dataSet[holder.adapterPosition].name}")).child("stock").setValue(originalStock.toString())
                else
                    database.child("${dataSet[holder.adapterPosition].kind}").child(convertDrinkName("${dataSet[holder.adapterPosition].name}")).child("stock").setValue(originalStock.toString())
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun convertBurgerName(name: String) : String {
        var idx = 0
        for (i in hamsName) {
            if (i.equals(name))
                break
            idx++
        }
        return hamsKey[idx]
    }
    fun convertDrinkName(name: String) : String {
        var idx = 0
        for (i in drinksName) {
            if (i.equals(name))
                break
            idx++
        }
        return drinksKey[idx]
    }
    fun convertSideName(name: String) : String {
        var idx = 0
        for (i in sidesName) {
            if (i.equals(name))
                break
            idx++
        }
        return sidesKey[idx]
    }

}