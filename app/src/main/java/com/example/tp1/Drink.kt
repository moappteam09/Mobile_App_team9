package com.example.tp1

class Drink {
    var image : String = ""
    var name : String = ""
    var price : String = ""

    constructor()

    constructor(image: String, name : String, price : String) {
        this.image = image
        this.name = name
        this.price = price
    }
}