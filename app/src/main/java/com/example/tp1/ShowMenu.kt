package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.renderscript.Sampler.Value
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tp1.MainActivity.Companion.orderlist
import com.example.tp1.databinding.ItemLayoutPlusBinding
import com.example.tp1.databinding.SelectMenuBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

//메뉴를 선택하는 화면을 보여주는 클래스
//spinner구현해야함

lateinit var orderListToPay: orderSet

class ShowMenu  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val binding = SelectMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // val origStock = intent.getSerializableExtra("origStock") as ArrayList<AllMenuStock>
        var inform_ham = intent.getStringExtra("Patty_and_Taste")
        if (inform_ham.isNullOrEmpty())
            inform_ham = "0 0"
        var patty = inform_ham?.slice(IntRange(0,0))
        var taste = inform_ham?.slice(IntRange(2,2))

        var cnt = 0 //burger의 item 번호를 알려주기위한 변수 선언

        Log.d("Data", "${inform_ham}")

        //스피너 태그 id로 변수에 저장해주기
        val spinner_taste = findViewById<Spinner>(R.id.spin_taste)
        val spinner_patty = findViewById<Spinner>(R.id.spin_patty)

        //스피너 어댑터에 연결해주기
        spinner_taste.adapter = ArrayAdapter.createFromResource(this, R.array.taste, android.R.layout.simple_spinner_item)
        binding.spinTaste.setSelection(taste!!.toInt())
        spinner_patty.adapter = ArrayAdapter.createFromResource(this, R.array.patty, android.R.layout.simple_spinner_item)
        binding.spinPatty.setSelection(patty!!.toInt())

        Log.d("SpinTest", "value : ${binding.spinPatty.selectedItem}, ${binding.spinTaste.selectedItem}")
        ////////////////메뉴 보여주는 리사이클러뷰에 데이터 불러와서 넣는 과정 구현////////////////
        val hamlist = ArrayList<Hamburger>() //햄버거 객체를 저장하는 list
        val drinklist = ArrayList<Drink>() //음료 객체를 저장하는 list
        val sidelist = ArrayList<Side>() //사이드메뉴 객체를 저장하는 list
        val alllist = ArrayList<AllMenu>() //모든 메뉴의 이미지, 이름, 가격이 들어가있는 클래스의 list
        var hamall = ArrayList<AllMenu>() //alllist에서 햄버거만 저장
        var drinkall = ArrayList<AllMenu>() //alllist에서 드링크만 저장
        var sideall = ArrayList<AllMenu>() //alllist에서 사이드만 저장

        var all_hamburger = Firebase.database.reference// 파이어베이스 DB객체를 레퍼런스함.

        //일단 DB에 있는 모든 메뉴들을 불러와서 저장해놓기 (전체 메뉴를 보여주기 위함)
        all_hamburger.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children) {
                    when {
                        "hamburger".equals(ds.key) -> {
                            val hamall = snapshot.child("hamburger")
                            Log.d("Database","Value : ${hamall.childrenCount}")
                            for(item in hamall.children) {
                                var image : String = item.child("image").value as String
                                var name : String = item.child("name").value as String
                                var price : String = item.child("price").value as String
                                var patty : String = item.child("patty").value as String
                                var taste : String = item.child("taste").value as String
                                var left : String = item.child("stock").value as String

                                //받은 데이터를 Allmenu클래스에 담기
                                alllist.add(AllMenu(image, name, price, patty, taste, left.toInt(), 1, 0))
                            }
                        }
                    }
                }
                for(ds in snapshot.children) {
                    when {
                        "drink".equals(ds.key) -> {
                            val driall = snapshot.child("drink")
                            Log.d("Database","Value : ${driall.childrenCount}")
                            for(item in driall.children) {
                                var image : String = item.child("image").value as String
                                var name : String = item.child("name").value as String
                                var price : String = item.child("price").value as String
                                //받은 데이터를 Allmenu클래스에 담기
                                alllist.add(AllMenu(image, name, price, "", "",0, 1, 2 ))
                            }
                        }
                    }
                }
                for(ds in snapshot.children) {
                    when {
                        "side".equals(ds.key) -> {
                            val sideall = snapshot.child("side")
                            Log.d("Database","Value : ${sideall.childrenCount}")
                            for(item in sideall.children) {
                                var image : String = item.child("image").value as String
                                var name : String = item.child("name").value as String
                                var price : String = item.child("price").value as String
                                //받은 데이터를 Allmenu클래스에 담기
                                alllist.add(AllMenu(image, name, price, "", "", 0, 1, 1))
                            }
                        }
                    }
                }
                //햄버거만 저장
                for(i in 0..alllist.size-1) {
                    if(alllist[i].type == 0) {
                        hamall.add(alllist[i])
                    }
                }
                //음료만 저장
                for(i in 0..alllist.size-1) {
                    if(alllist[i].type == 2) {
                        drinkall.add(alllist[i])
                    }
                }
                //사이드만 저장
                for(i in 0..alllist.size-1) {
                    if(alllist[i].type == 1) {
                        sideall.add(alllist[i])
                    }
                }
                var afterall = ArrayList<AllMenu>()
                for(i in 0..alllist.size-1) {
                    if(alllist[i].type == 0) { //햄버거면 체크해줌
                        if(binding.spinPatty.selectedItem.equals("전체") && binding.spinTaste.selectedItem.equals("전체")) {
                            afterall.add(alllist[i])
                        }
                        else if(alllist[i].patty.equals(binding.spinPatty.selectedItem) && alllist[i].taste.equals(binding.spinTaste.selectedItem)) {
                            afterall.add(alllist[i])
                        }
                    }
                    else {
                        afterall.add(alllist[i])
                    }
                }
                binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                binding.menuRecycler.adapter = ShowAll_Adapter(afterall, -1, binding)
            }

            override fun onCancelled(error: DatabaseError) {
                print(error.message)
            }
        })
        //전체 버튼을 눌렀을 경우, 햄버거와 사이드 음료 모든 메뉴를 리사이클러뷰로 보여줌
        binding.watchAll.setOnClickListener() {
            binding.spinPatty.visibility = View.VISIBLE
            binding.spinTaste.visibility = View.VISIBLE
            var afterall = ArrayList<AllMenu>()
            //맛만 전체를 고른 경우라면?
            if(binding.spinTaste.selectedItem.equals("전체") && !binding.spinPatty.selectedItem.equals("전체")) {
                for(i in 0..alllist.size-1) {
                    if(alllist[i].type == 0) { //햄버거면 체크해줌
                        if(alllist[i].patty.equals(binding.spinPatty.selectedItem)) {
                            afterall.add(alllist[i])
                        }
                    }
                    else {
                        afterall.add(alllist[i])
                    }
                }
                binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                binding.menuRecycler.adapter = ShowAll_Adapter(afterall, -1, binding)
            }
            //패티만 전체를 고른 경우라면? Taste만 체크해주면됨
            else if(binding.spinPatty.selectedItem.equals("전체") && !binding.spinTaste.selectedItem.equals("전체")) {
                for(i in 0..alllist.size-1) {
                    if(alllist[i].type == 0) { //햄버거면 체크해줌
                        if(alllist[i].taste.equals(binding.spinTaste.selectedItem)) {
                            afterall.add(alllist[i])
                        }
                    }
                    else {
                        afterall.add(alllist[i])
                    }
                }
                binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                binding.menuRecycler.adapter = ShowAll_Adapter(afterall, -1, binding)
            }
            else if(binding.spinPatty.selectedItem.equals("전체") && binding.spinTaste.selectedItem.equals("전체")) {
                Log.d("전체를 체크한 경우", "패티랑 맛 둘 다 전체 선택 후 전체버튼 클릭!")
                binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                binding.menuRecycler.adapter = ShowAll_Adapter(alllist, -1, binding)
            }
            else {
                for(i in 0..alllist.size-1) {
                    if(alllist[i].type == 0) { //햄버거면 체크해줌
                        if(alllist[i].patty.equals(binding.spinPatty.selectedItem) && alllist[i].taste.equals(binding.spinTaste.selectedItem)) {
                            afterall.add(alllist[i])
                        }
                    }
                    else {
                        afterall.add(alllist[i])
                    }
                }
                binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                binding.menuRecycler.adapter = ShowAll_Adapter(afterall, -1, binding)
            }
        }

        //햄버거 버튼을 눌렀을 경우 햄버거 메뉴를 보여줌
        binding.watchHam.setOnClickListener() {
            binding.spinPatty.visibility = View.VISIBLE
            binding.spinTaste.visibility = View.VISIBLE
            var afterall = ArrayList<AllMenu>()
            if(binding.spinTaste.selectedItem.equals("전체") && !binding.spinPatty.selectedItem.equals("전체")) {
                for(i in 0..alllist.size-1) {
                    if(alllist[i].type == 0) { //햄버거면 체크해줌
                        if(alllist[i].patty.equals(binding.spinPatty.selectedItem)) {
                            afterall.add(alllist[i])
                        }
                    }
                    else {
                        afterall.add(alllist[i])
                    }
                }
                binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                binding.menuRecycler.adapter = ShowAll_Adapter(afterall, -1, binding)
            }
            //패티만 전체를 고른 경우라면? Taste만 체크해주면됨
            else if(binding.spinPatty.selectedItem.equals("전체") && !binding.spinTaste.selectedItem.equals("전체")) {
                for(i in 0..alllist.size-1) {
                    if(alllist[i].type == 0) { //햄버거면 체크해줌
                        if(alllist[i].taste.equals(binding.spinTaste.selectedItem)) {
                            afterall.add(alllist[i])
                        }
                    }
                    else {
                        afterall.add(alllist[i])
                    }
                }
                binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                binding.menuRecycler.adapter = ShowAll_Adapter(afterall, -1, binding)
            }
            else if(binding.spinPatty.selectedItem.equals("전체") && binding.spinTaste.selectedItem.equals("전체")) {
                Log.d("전체를 체크한 경우", "패티랑 맛 둘 다 전체 선택 후 전체버튼 클릭!")
                for(i in 0..alllist.size-1) {
                    if(alllist[i].type == 0) {
                        afterall.add(alllist[i])
                    }
                }
                binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                binding.menuRecycler.adapter = ShowAll_Adapter(afterall, -1, binding)
            }
            else {
                for(i in 0..hamall.size-1) {
                    if(alllist[i].patty.equals(binding.spinPatty.selectedItem) && alllist[i].taste.equals(binding.spinTaste.selectedItem)) {
                        afterall.add(hamall[i])
                    }
                }
                binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                binding.menuRecycler.adapter = ShowAll_Adapter(afterall, 0, binding)
            }
        }

        //음료 버튼 클릭했을 때 DB에서 데이터 받아와서 넘겨주는 부분//
        binding.watchBev.setOnClickListener() {
            binding.spinPatty.visibility = View.INVISIBLE
            binding.spinTaste.visibility = View.INVISIBLE
            binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
            binding.menuRecycler.adapter = ShowAll_Adapter(drinkall, 2, binding)
        }

        //사이드 버튼 클릭했을 때 DB에서 데이터 받아와서 넘겨주는 부분//
        binding.watchSide.setOnClickListener() {
            binding.spinPatty.visibility = View.INVISIBLE
            binding.spinTaste.visibility = View.INVISIBLE
            binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
            binding.menuRecycler.adapter = ShowAll_Adapter(sideall, 1, binding)
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////

        val order = findViewById <Button>(R.id.btn_buy)  //주문하기 버튼
        order.setOnClickListener{
            if (orderListToPay.ham.size > 0 || orderListToPay.side.size > 0 || orderListToPay.drink.size > 0) {
                val intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra("whereFrom", 1)
                intent.putExtra("orderSet", orderListToPay.getAll())
                intent.putExtra("orderSet2", orderListToPay.getAllByDivision())
                intent.putExtra("orderSet3", orderListToPay.getAllByDivisionPlusPrice())
                intent.putExtra("totalPrice", orderListToPay.getTotalPrice())
                intent.putExtra("hamOrders", orderListToPay.getHamburger())
                //intent.putExtra("origStock", origStock as java.io.Serializable)
                startActivity(intent)
            }
        }
        binding.selectmenuRecycler.layoutManager = LinearLayoutManager(this)

        //스핀버튼 select이벤트 처리하는 부분
        spinner_patty.onItemSelectedListener = object : //패티 스핀버튼 change할 경우
        AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //문자열로 패티 종류 저장되어있음 binding.spinPatty.selectedItem
                //문자열로 맛 종류 저장되어있음 binding.spinTaste.selectedItem
                if(binding.spinPatty.selectedItem.equals("전체")) {
                    var spinchangePatty = ArrayList<AllMenu>()
                    for(i in 0..alllist.size-1) {
                        if(alllist[i].type == 0) {
                            if(binding.spinTaste.selectedItem.equals("전체")) {
                                spinchangePatty.add(alllist[i])
                            }
                            else if (alllist[i].taste.equals(binding.spinTaste.selectedItem)) {
                                spinchangePatty.add(alllist[i])
                            }
                        }
                        else {
                            spinchangePatty.add(alllist[i])
                        }
                    }
                    binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                    binding.menuRecycler.adapter = ShowAll_Adapter(spinchangePatty, -1, binding)
                }
                else {
                    var spinchangePatty = ArrayList<AllMenu>()
                    for(i in 0..alllist.size-1) {
                        if(alllist[i].type==0) {
                            if(binding.spinTaste.selectedItem.equals("전체")) {
                                if(alllist[i].patty.equals(binding.spinPatty.selectedItem)) {
                                    spinchangePatty.add(alllist[i])
                                }
                            }
                            else if(alllist[i].patty.equals(binding.spinPatty.selectedItem) && alllist[i].taste.equals(binding.spinTaste.selectedItem)) {
                                spinchangePatty.add(alllist[i])
                            }
                        }
                        else {
                            spinchangePatty.add(alllist[i])
                        }
                    }
                    binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                    binding.menuRecycler.adapter = ShowAll_Adapter(spinchangePatty, -1, binding)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        spinner_taste.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //문자열로 패티 종류 저장되어있음 binding.spinPatty.selectedItem
                //문자열로 맛 종류 저장되어있음 binding.spinTaste.selectedItem
                if(binding.spinTaste.selectedItem.equals("전체")) {
                    var spinchangeTaste = ArrayList<AllMenu>()
                    for(i in 0..alllist.size-1) {
                        //햄버거만 체크해주면 됨!
                        if(alllist[i].type == 0) {
                            if(binding.spinPatty.selectedItem.equals("전체")) {
                                spinchangeTaste.add(alllist[i])
                            }
                            else if (alllist[i].patty.equals(binding.spinPatty.selectedItem)) {
                                spinchangeTaste.add(alllist[i])
                            }
                        }
                        else {
                            spinchangeTaste.add(alllist[i])
                        }
                    }
                    binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                    binding.menuRecycler.adapter = ShowAll_Adapter(spinchangeTaste, -1, binding)
                }
                else {
                    var spinchangeTaste = ArrayList<AllMenu>()
                    for(i in 0..alllist.size-1) {
                        if(alllist[i].type == 0) {
                            if(binding.spinPatty.selectedItem.equals("전체")) {
                                if(alllist[i].taste.equals(binding.spinTaste.selectedItem)) {
                                    spinchangeTaste.add(alllist[i])
                                }
                            }
                            else if(alllist[i].patty.equals(binding.spinPatty.selectedItem) && alllist[i].taste.equals(binding.spinTaste.selectedItem)) {
                                spinchangeTaste.add(alllist[i])
                            }
                        }
                        else {
                            spinchangeTaste.add(alllist[i])
                        }
                    }
                    binding.menuRecycler.layoutManager = GridLayoutManager(this@ShowMenu, 3)
                    binding.menuRecycler.adapter = ShowAll_Adapter(spinchangeTaste, -1, binding)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }




    }
}
class orderMenuHolder (val binding : ItemLayoutPlusBinding) : RecyclerView.ViewHolder(binding.root)

class orderMenu_Adapter(val dataSet : MutableList<AllMenu>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return orderMenuHolder(ItemLayoutPlusBinding.inflate(LayoutInflater.from(parent.context),
        parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("OrderSet", "name : ${dataSet[position]}")
        Log.d("dataSize", "size : ${dataSet.size}")
        val viewholder = (holder as orderMenuHolder).binding
        //companion object를 이용해 전역변수로 선언한 var orderlist = mutableListOf<orderSet>() 사용
        var orderham = ArrayList<Hamburger>() //햄버거들의 리스트를 저장할 객체 리스트, Adapter돌때마다 초기화
        var orderdrink = ArrayList<Drink>() //음료들의 리스트를 저장할 객체 리스트, Adapter돌때마다 초기화
        var orderside = ArrayList<Side>() //사이드들의 리스트를 저장할 객체 리스트, Adapter돌때마다 초기화
        orderlist = mutableListOf() //Adapter돌때마다 초기화
        orderListToPay = orderSet(orderham, orderdrink, orderside)
        //이제 위의 리스트에 멤버를 다 넣은 다음에
        //orderlist에 마지막에 다 넣어서 주문하기 버튼을 누르면 intent로 넘겨준다
        for(i in 0..dataSet.size-1) {
            Log.d("CheckData", "OrderCount : ${dataSet[position].orderCount}")
        }
        for(i in 0..dataSet.size-1) {
            if(dataSet[i].type==0) { //DataSet안에 들어있는 놈이 햄버거라면
                orderham.add(Hamburger(dataSet[i].image
                    , dataSet[i].name
                    , dataSet[i].price
                    , dataSet[i].patty
                    , dataSet[i].taste
                    , 0
                    , dataSet[i].orderCount))
                Log.d("OrderSet", "Ham : ${dataSet[i].orderCount}")
            }
            else if(dataSet[i].type==1) { //DataSet안에 들어있는 놈이 사이드라면
                orderside.add(Side(dataSet[i].image
                    , dataSet[i].name
                    , dataSet[i].price
                    , dataSet[i].orderCount))
            }
            else if(dataSet[i].type==2) { //DataSet안에 들어있는 놈이 음료라면
                orderdrink.add(Drink(dataSet[i].image
                    , dataSet[i].name
                    , dataSet[i].price
                    , dataSet[i].orderCount))
            }
        }
        orderlist.add(orderSet(orderham, orderdrink, orderside))
        Log.d("Check orderList(Burger)", "${orderham}")
        Log.d("Check orderList(Drink)", "${orderdrink}")
        Log.d("Check orderList(Side)", "${orderside}")

        Glide.with(holder.itemView)
            .load(dataSet[position].image)
            .into(viewholder.orderimg)
        viewholder.ordername.text = dataSet[position].name
        viewholder.orderprice.text = (dataSet[position].price.toInt()*dataSet[position].orderCount).toString()
        viewholder.ordercnt.text = dataSet[position].orderCount.toString()
        viewholder.orederDelete.setOnClickListener() {//삭제버튼을 눌렀을 때
            removeItem(position)
        }
        viewholder.orderplus.setOnClickListener() {
            viewholder.ordercnt.text = (viewholder.ordercnt.text.toString().toInt() + 1).toString()
            dataSet[position].orderCount = viewholder.ordercnt.text.toString().toInt()
            Log.d("PlusBtn", "Cnt : ${dataSet[position].orderCount}")
            viewholder.orderprice.text = (dataSet[position].price.toInt() * dataSet[position].orderCount).toString()
            notifyDataSetChanged()
        }
        viewholder.orderminus.setOnClickListener() {
            viewholder.ordercnt.text = (viewholder.ordercnt.text.toString().toInt() - 1).toString()
            dataSet[position].orderCount = viewholder.ordercnt.text.toString().toInt()
            Log.d("MinusBtn", "Cnt : ${dataSet[position].orderCount}")
            viewholder.orderprice.text = (dataSet[position].price.toInt() * dataSet[position].orderCount).toString()
            notifyDataSetChanged()
        }
        //주문하기 버튼을 클릭했을 때 dataSet에 담긴 녀석들의 정보를 orderSet으로 전달해주어야 한다.

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun removeItem(position : Int) {
        if(position >= 0) {
            dataSet[position].orderCount = 1
            dataSet.removeAt(position)
            notifyDataSetChanged()
        }
    }
}
