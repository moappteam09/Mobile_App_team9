package com.example.tp1

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tp1.databinding.AdminBinding
import com.example.tp1.databinding.ItemLayoutStockBinding
import com.example.tp1.databinding.MenuViewholderBinding

class AdminList_ViewHolder (val binding : ItemLayoutStockBinding) : RecyclerView.ViewHolder(binding.root)

class AdminList_Adapter(val dataSet: MutableList<Hamburger>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AdminList_ViewHolder(ItemLayoutStockBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("RecyclerView", "onBindViewHoleder() : ${position}")
        val binding = (holder as AdminList_ViewHolder).binding
        //id값들
        //메뉴 이미지 : stock_img
        //메뉴 이름 : stock_menu_name
        //메뉴 재고 : stock_current_stock
        //메뉴 변경 값 : stock_edit_stock


        //binding.stockImg.text = dataSet[position].image
        binding.stockMenuName.text = dataSet[position].name
        binding.stockEditStock.text = "0"
        binding.stockCurrentStock.text = dataSet[position].left as String
    }

    override fun getItemCount(): Int {
        Log.d("RecyclerView", "init data size: ${dataSet.size}")
        return dataSet.size
    }

}