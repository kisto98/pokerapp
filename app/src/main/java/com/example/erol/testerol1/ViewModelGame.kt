package com.example.erol.testerol1

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log


class ViewModelGame: ViewModel() {

    companion object {
        private val TAG = "ViewModelGame"
    }

    private var deck = Deck()

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

        deck = Deck()
        deck?.shuffle()
        cash.postValue(Constants.defaultCash)
    }
}



