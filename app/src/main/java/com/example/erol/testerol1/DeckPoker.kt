package com.example.erol.testerol1

import android.util.Log
import java.util.*



class DeckPoker {

    private val TAG = "Deck"

    val cards: ArrayList<Card> = ArrayList()

    val pledges = arrayOf<String>("herc", "karo", "pik", "tref")

    init {
        Log.i(TAG, "init")
        pledges.forEach {
            Log.i("er", "" + it)
            for (i in 1..14) {
                if (i == 11) continue
                cards.add(Card(it, i))
            }
        }
        Log.i(TAG,"Imam ${cards.size} karata")

        cards.forEach{
            Log.i(TAG,"${it.number} ${it.pledge}")
        }

    }

    fun shuffle(){
        Log.i(TAG, "shuffle")
        Collections.shuffle(cards)
    }

    fun getNextCard() : Card {
        Log.i(TAG, "getNextCard")
        return cards.removeAt(0)
    }

}








