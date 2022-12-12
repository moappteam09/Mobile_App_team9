package com.example.tp1


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tp1.databinding.MenuViewholderBinding

class ShowAll_ViewHolder (val binding : MenuViewholderBinding) : RecyclerView.ViewHolder(binding.root)
class ShowAll_Adapter(val dataSet : MutableList<AllMenu>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShowAll_ViewHolder(MenuViewholderBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ShowAll_ViewHolder).binding

        Glide.with(holder.itemView)
            .load(dataSet[position].image)
            .into(binding.menuImg)
        binding.menuName.text = "${dataSet[position].name}"
        binding.menuPrice.text = "${dataSet[position].price}"
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}

