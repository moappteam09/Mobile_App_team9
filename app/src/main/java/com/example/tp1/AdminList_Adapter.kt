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
            database.child("${dataSet[holder.adapterPosition].kind}").child(convertBurgerName("${dataSet[holder.adapterPosition].name}")).child("stock").setValue(originalStock.toString())
            notifyDataSetChanged()
        }
        binding.stockDec.setOnClickListener{
            val database = Firebase.database.reference
            originalStock -= 1
            database.child("${dataSet[holder.adapterPosition].kind}").child(convertBurgerName("${dataSet[holder.adapterPosition].name}")).child("stock").setValue(originalStock.toString())
            notifyDataSetChanged()
        }


    }

    override fun getItemCount(): Int {
        return dataSet.size
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
            "undefined"
    }

}