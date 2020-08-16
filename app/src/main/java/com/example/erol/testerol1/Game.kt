package com.example.erol.testerol1

import android.util.Log



class Game {

    private val TAG = "Game"

    private var deck : Deck? = null

    fun start() {
        Log.i(TAG, "start")
        deck = Deck()
        deck?.shuffle()

    }

    fun playMinMax() : Card? {
        val card = deck?.getNextCard()
        Log.i(TAG, "playMinMax ${card.toString()}")
        return card
    }

}