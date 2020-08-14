package com.example.erol.testerol1

/**
 * Created by erol on 25/01/19.
 */

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
