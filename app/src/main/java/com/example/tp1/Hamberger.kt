package com.example.tp1

class Hamburger {
    var image : String = ""
    var name : String = ""
    var price : String = ""
    var patty : String = ""
    var taste : String = ""
    var left  : Int = 0
    var orderCount : Int = 0

    constructor() // 디폴트 생성자 필수로 해주어야함
    constructor(image : String, name : String, price : String, patty : String, taste : String, left : Int, orderCount : Int) {
        this.image = image
        this.name = name
        this.price = price
        this.patty = patty
        this.taste = taste
        this.left = left
        this.orderCount = orderCount
    }

}