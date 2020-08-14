package com.example.erol.testerol1

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log


/**
 * Created by erol on 05/02/19.
 */
class ViewModelGamePoker: ViewModel() {

    companion object {
        private val TAG = "ViewModelGamePoker"
    }

    private var deck = DeckPoker()

    val cash = MutableLiveData<Int>()
    val currentCard = MutableLiveData<Card>()

    fun playNewCard(userPlayedLow: Boolean){
        val card = deck.getNextCard()
        currentCard.postValue(card)

        card?.let {
            if (((it.number < 7) && userPlayedLow) || ((it.number >= 7) && !userPlayedLow)) {
                Log.i(TAG, "karta - pogodjeno")
                cash.value?.let {
                    cash.postValue(it * 2)
                }
            } else {
                cash.postValue(0)
                Log.i(TAG, "playNewCard - puko si")
            }
        }
    }

    fun startGame(){

        deck = DeckPoker()
        deck?.shuffle()
        cash.postValue(Constants.defaultCash)
    }

    /*Creating arrayList and filling him*/

    fun deal(map: MutableMap <Int, Card?>){
        map.keys.forEach{key ->
                if (map[key] == null || (map[key]?.hidden==true)){
                    map[key] = deck.getNextCard()
                }

        }
    }
}



