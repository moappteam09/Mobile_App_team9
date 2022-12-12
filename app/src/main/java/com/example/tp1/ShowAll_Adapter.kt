package com.example.tp1


import android.content.Context
import android.icu.lang.UCharacter.VerticalOrientation
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tp1.databinding.MenuViewholderBinding
import com.example.tp1.databinding.SelectMenuBinding

class ShowAll_ViewHolder (val binding : MenuViewholderBinding) : RecyclerView.ViewHolder(binding.root)
class ShowAll_Adapter(val dataSet : MutableList<AllMenu>, val type : Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShowAll_ViewHolder(MenuViewholderBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ShowAll_ViewHolder).binding

        when {
            type == -1 -> { //전체를 다 보여줄 경우
                Glide.with(holder.itemView)
                    .load(dataSet[position].image)
                    .into(binding.menuImg)
                binding.menuName.text = "${dataSet[position].name}"
                binding.menuPrice.text = "${dataSet[position].price}"
            }
            type == 0 -> { // 햄버거만 보여주는 경우라면?
                Glide.with(holder.itemView)
                    .load(dataSet[position].image)
                    .into(binding.menuImg)
                binding.menuName.text = "${dataSet[position].name}"
                binding.menuPrice.text = "${dataSet[position].price}"
            }
            type == 1 -> {
                Glide.with(holder.itemView)
                    .load(dataSet[position].image)
                    .into(binding.menuImg)
                binding.menuName.text = "${dataSet[position].name}"
                binding.menuPrice.text = "${dataSet[position].price}"
            }
            type == 2 -> {
                Glide.with(holder.itemView)
                    .load(dataSet[position].image)
                    .into(binding.menuImg)
                binding.menuName.text = "${dataSet[position].name}"
                binding.menuPrice.text = "${dataSet[position].price}"
            }
        }



//        holder.itemView.setOnClickListener() {
//            Log.d("ItemClick", "You Click : ${dataSet[position].name}, ${dataSet[position].price}")
//            dataSet2.ham.add(Hamburger(dataSet[position].image, dataSet[position].name
//                , dataSet[position].price
//                , dataSet[position].patty
//                , dataSet[position].taste
//                , dataSet[position].left
//                , 0))
//            (binding2.selectmenuRecycler.adapter as menuSelect_Adapter).notifyDataSetChanged()
//        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}

