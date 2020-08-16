package com.example.erol.testerol1



class Card(aPledge: String, aNumber: Int) {

    val pledge = aPledge
    val number = aNumber
    var hidden = false


    override fun toString(): String {
        return "$number $pledge"
    }

    fun toogle(){

        hidden = !hidden
    }

}
