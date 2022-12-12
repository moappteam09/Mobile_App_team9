package com.example.tp1

import android.util.Log

class orderSet {
    var ham = mutableListOf<Hamburger>()
    var drink = mutableListOf<Drink>()
    var side = mutableListOf<Side>()

    constructor()

    constructor(ham : MutableList<Hamburger>, jui : MutableList<Drink>, side : MutableList<Side>) {
        this.ham = ham
        this.drink = jui
        this.side = side
    }

    fun getName(): String { // 대표 이름 불러오기
        var string = ""
        if (ham.size == 1) {
            if (drink.size + side.size > 0)
                string = ham[0].name + " 외 " + (drink.size + side.size).toString()
            else
                string = ham[0].name
        }
        else if (ham.size > 1)
            string = ham[0].name + " 외" + (ham.size - 1 + drink.size + side.size).toString()
        else if (drink.size == 1) {
            if (drink.size + side.size > 0)
                string = drink[0].name + " 외 " + side.size.toString()
            else
                string = drink[0].name
        }
        else if (drink.size > 1)
            string = drink[0].name + " 외" + (drink.size - 1 + side.size).toString()
        else if (side.size == 1) {
            string = side[0].name
        }
        else if (side.size > 1)
            string = side[0].name + " 외" + (side.size - 1).toString()
        else
            string = "Empty Set"
        return string
    }

    fun getTotalPrice(): Int {
        var total = 0
        for (i in ham)
            total += i.price.toInt()
        for (i in drink)
            total += i.price.toInt()
        for (i in side)
            total += i.price.toInt()
        return total
    }

    fun getAll(): String {
        var string = ""
        for (i in ham)
            string += i.name + "|" + i.price + ","
        for (i in drink)
            string += i.name + "|" + i.price + ","
        for (i in side)
            string += i.name + "|" + i.price + ","
        string = string.slice(IntRange(0, string.length - 2))
        return string
    }

    fun debugAll() {
        Log.d("debug", "Hamburgers:")
        for (i in ham)
            Log.d("debug", i.name)
        Log.d("debug", "Drinks:")
        for (i in drink)
            Log.d("debug", i.name)
        Log.d("debug", "Sides:")
        for (i in side)
            Log.d("debug", i.name)
    }
}