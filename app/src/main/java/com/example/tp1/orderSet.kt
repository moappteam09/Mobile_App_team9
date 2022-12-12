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

    fun getFullName(): String { // 대표 이름 긴 포맷으로 불러오기
        var string = ""
        for (i in ham)
            string += i.name + " x" + i.orderCount + ", "
        for (i in side)
            string += i.name + " x" + i.orderCount + ", "
        for (i in drink)
            string += i.name + " x" + i.orderCount + ", "

        return string.slice(IntRange(0, string.length - 3))
    }

    fun getShortName(): String { // 대표 이름 짧은 포맷으로 불러오기
        var string = ""
        if (ham.size == 1) {
            if (drink.size + side.size > 0)
                string = ham[0].name + " 외 " + (drink.size + side.size).toString()
            else
                string = ham[0].name
        }
        else if (ham.size > 1)
            string = ham[0].name + " 외" + (ham.size - 1 + drink.size + side.size).toString()
        else if (side.size == 1) {
            if (drink.size > 0)
                string = side[0].name + " 외 " + drink.size.toString()
            else
                string = side[0].name
        }
        else if (side.size > 1)
            string = side[0].name + " 외" + (side.size - 1 + drink.size).toString()
        else if (drink.size == 1) {
            string = drink[0].name
        }
        else if (drink.size > 1)
            string = drink[0].name + " 외" + (drink.size - 1).toString()
        else
            string = "Empty Set"
        return string
    }

    fun getTotalPrice(): Int {
        var total = 0
        for (i in ham)
            total += i.price.toInt() * i.orderCount
        for (i in side)
            total += i.price.toInt() * i.orderCount
        for (i in drink)
            total += i.price.toInt() * i.orderCount
        return total
    }

    fun getAll(): String {
        var string = ""
        for (i in ham)
            string += i.name + "!" + i.orderCount + "|" + i.price.toInt() * i.orderCount + ","
        for (i in side)
            string += i.name + "!" + i.orderCount + "|" + i.price.toInt() * i.orderCount + ","
        for (i in drink)
            string += i.name + "!" + i.orderCount + "|" + i.price.toInt() * i.orderCount + ","
        string = string.slice(IntRange(0, string.length - 2))
        return string
    }

    fun getHamburger() : String {
        var string = ""
        for (i in ham)
            string += i.name + "!" + i.orderCount + "!" + i.left + ","
        string = string.slice(IntRange(0, string.length - 2))
        return string
    }

    fun getRepresentedImage() : String {
        return if (ham.size > 0)
            ham[0].image
        else if (side.size > 0)
            side[0].image
        else if (drink.size > 0)
            drink[0].image
        else
            "Empty Set!"
    }

    fun debugAll() {
        Log.d("debug", "Hamburgers:")
        for (i in ham)
            Log.d("debug", i.name)
        Log.d("debug", "Drinks:")
        for (i in side)
            Log.d("debug", i.name)
        Log.d("debug", "Sides:")
        for (i in drink)
            Log.d("debug", i.name)
    }
}