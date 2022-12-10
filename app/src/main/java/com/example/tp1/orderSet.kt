package com.example.tp1

class orderSet {
    var ham : Hamberger = Hamberger()
    var drink : Drink = Drink()
    var side : Side = Side()

    constructor()

    constructor(ham : Hamberger, jui : Drink, side : Side) {
        this.ham = ham
        this.drink = jui
        this.side = side
    }
}