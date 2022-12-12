package com.example.tp1

class AllMenu {
    var image : String = ""
    var name : String = ""
    var price : String = ""
    var patty : String = ""
    var taste : String = ""
    var left  : Int = 0
    var orderCount : Int = 0
    var type : Int = 0 //햄버거 = 0, 사이드 = 1, 음료 = 2
    constructor()
    constructor(image : String
                , name : String
                , price : String
                , patty : String
                , taste : String
                , left : Int
                , orderCount : Int
                , type : Int) {
        this.image = image
        this.name = name
        this.price = price
        this.patty = patty
        this.taste = taste
        this.left = left
        this.orderCount = orderCount
        this.type = type
    }
}