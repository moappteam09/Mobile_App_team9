package com.example.tp1

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tp1.databinding.MenuViewholderBinding

class ShowMenu_ViewHolder (val binding : MenuViewholderBinding) : RecyclerView.ViewHolder(binding.root)

class ShowMenuList_Adapter(val dataSet: MutableList<Hamburger>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShowMenu_ViewHolder(MenuViewholderBinding.inflate(LayoutInflater.from(parent.context),
        parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("RecyclerView", "onBindViewHoleder() : ${position}")
        val binding = (holder as ShowMenu_ViewHolder).binding
        //id값들
        //메뉴 이미지 : menu_img
        //메뉴 이름 : menu_name
        //메뉴 가격 : menu_price
        for(i in dataSet) {
//            binding.menuImg.setImageURI()
            binding.menuName.text = "${i.name}"
            binding.menuPrice.text = "${i.price}"
        }
    }

    override fun getItemCount(): Int {
        Log.d("RecyclerView", "init data size: ${dataSet.size}")
        return dataSet.size
    }

}