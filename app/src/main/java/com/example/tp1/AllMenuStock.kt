package com.example.tp1

class AllMenuStock : java.io.Serializable{
    var kind : String = ""
    var image : String = ""
    var name : String = ""
    var price : String = ""
    var left : String = ""
    constructor()
    constructor(kind : String, image : String, name : String, price : String, left : String) {
        this.kind = kind
        this.image = image
        this.name = name
        this.price = price
        this.left = left
    }
}