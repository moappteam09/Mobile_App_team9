package com.example.tp1

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tp1.databinding.SelectWhatViewholderBinding

class SelectItemViewHolder(val binding: SelectWhatViewholderBinding): RecyclerView.ViewHolder(binding.root)

class SelectItemAdapter(private val dataSet: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return SelectItemViewHolder(SelectWhatViewholderBinding.inflate(LayoutInflater.from(parent.context),
        parent, false))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("RecyclerView", "onBindViewHolder() : $position")
        val binding = (holder as SelectItemViewHolder).binding
        //id를 찾을 때 _를 빼고 검색해야함!
        binding.checkText.text = dataSet[position] //뷰에 데이터 출력

    }

    override fun getItemCount(): Int {
        Log.d("RecyclerView", "init data size : ${dataSet.size}")
        return dataSet.size
    }

}