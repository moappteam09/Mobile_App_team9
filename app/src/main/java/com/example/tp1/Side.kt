package com.example.tp1

class Side {
    var image : String = ""
    var name : String = ""
    var price : String = ""
    var orderCount : Int = 0
    var left : Int = 0

    constructor()
    constructor(image : String, name : String, price : String, orderCount : Int, left : Int) {
        this.image = image
        this.name = name
        this.price = price
        this.orderCount = orderCount
        this.left = left
    }
}