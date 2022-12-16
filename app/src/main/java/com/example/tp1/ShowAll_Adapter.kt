package com.example.tp1


import android.content.Context
import android.icu.lang.UCharacter.VerticalOrientation
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tp1.MainActivity.Companion.list
import com.example.tp1.databinding.MenuViewholderBinding
import com.example.tp1.databinding.SelectMenuBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class ShowAll_ViewHolder (val binding : MenuViewholderBinding) : RecyclerView.ViewHolder(binding.root)

class ShowAll_Adapter(val dataSet : MutableList<AllMenu>, val type : Int, val binding2 : SelectMenuBinding) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShowAll_ViewHolder(MenuViewholderBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ShowAll_ViewHolder).binding
        //orderSet으로 넘겨줄 각 메뉴 클래스의 리스트들 초기화
        var hamlist = ArrayList<Hamburger>()
        var sidelist = ArrayList<Side>()
        var drinklist = ArrayList<Drink>()


        when {
            type == -1 -> { //전체를 다 보여줄 경우
                Glide.with(holder.itemView)
                    .load(dataSet[position].image)
                    .into(binding.menuImg)
                binding.menuName.text = "${dataSet[position].name}"
                binding.menuPrice.text = "${dataSet[position].price}"
                if(dataSet[position].left == 0 && dataSet[position].type == 0) {
                    binding.menuImg.alpha = 0.5F
                    binding.menuName.alpha = 0.5F
                    binding.menuPrice.alpha = 0.5F
                }
            }
            type == 0 -> { // 햄버거만 보여주는 경우라면?
                Glide.with(holder.itemView)
                    .load(dataSet[position].image)
                    .into(binding.menuImg)
                binding.menuName.text = "${dataSet[position].name}"
                binding.menuPrice.text = "${dataSet[position].price}"
                if(dataSet[position].left == 0) {
                    binding.menuImg.alpha = 0.5F
                    binding.menuName.alpha = 0.5F
                    binding.menuPrice.alpha = 0.5F
                }
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
        //dataSet안의 값으로 type이 있으니, 그것으로 햄버거인지(0), 사이드인지(1), 음료인지(2) 판단하자
        holder.itemView.setOnClickListener() {
            Log.d("Clicked Item Inform", "name : ${dataSet[position].name}, price : ${dataSet[position].price}, type : ${dataSet[position].type}")

            if(dataSet[position].left==0 && dataSet[position].type==0) {

            }
            else {
                if(dataSet[position].type==0) { //햄버거를 담았을 경우 Hamburger클래스에 추가
                    list.add(dataSet[position])
                    binding2.selectmenuRecycler.adapter = orderMenu_Adapter(list)
                    binding2.selectmenuRecycler.addItemDecoration(
                        DividerItemDecoration(this@ShowAll_Adapter.binding2.selectmenuRecycler.context,
                            LinearLayoutManager.VERTICAL
                        )
                    )
//                var hamname = dataSet[position].name
//                var hamprice = dataSet[position].price
//                hamlist.add(Hamburger("", hamname, hamprice, "", "", 0, 0))

                }
                else if(dataSet[position].type==1) { //사이드를 담았을 경우 Side클래스에 추가
                    list.add(dataSet[position])
                    binding2.selectmenuRecycler.adapter = orderMenu_Adapter(list)
                    binding2.selectmenuRecycler.addItemDecoration(
                        DividerItemDecoration(this@ShowAll_Adapter.binding2.selectmenuRecycler.context,
                            LinearLayoutManager.VERTICAL
                        )
                    )
//                var sidename = dataSet[position].name
//                var sideprice = dataSet[position].price
//                sidelist.add(Side("", sidename, sideprice, 0))
                }
                else if(dataSet[position].type==2) { //음료를 담았을 경우 Drink 클래스에 추가
                    list.add(dataSet[position])
                    Log.d("listsize", "listSize : ${list.size}")
                    binding2.selectmenuRecycler.adapter = orderMenu_Adapter(list)
                    binding2.selectmenuRecycler.addItemDecoration(
                        DividerItemDecoration(this@ShowAll_Adapter.binding2.selectmenuRecycler.context,
                            LinearLayoutManager.VERTICAL
                        )
                    )
                }
            }


        }


    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}

