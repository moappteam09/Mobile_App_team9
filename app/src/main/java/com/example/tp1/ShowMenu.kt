package com.example.tp1

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.tp1.databinding.SelectMenuBinding

//메뉴를 선택하는 화면을 보여주는 클래스
//spinner구현해야함
class ShowMenu  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val binding = SelectMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //스피너 태그 id로 변수에 저장해주기
        val spinner_taste = findViewById<Spinner>(R.id.spin_taste)
        val spinner_patty = findViewById<Spinner>(R.id.spin_patty)

        //스피너 어댑터에 연결해주기
        spinner_taste.adapter = ArrayAdapter.createFromResource(this, R.array.taste, android.R.layout.simple_spinner_item)
        spinner_patty.adapter = ArrayAdapter.createFromResource(this, R.array.patty, android.R.layout.simple_spinner_item)

        val order = findViewById<Button>(R.id.btn_buy)  //주문하기 버튼
        order.setOnClickListener{
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }
    }
}