package com.example.erol.testerol1

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.pocetnakazina.*
import kotlinx.android.synthetic.main.poker_main.*
import java.util.*
import kotlin.system.exitProcess


class Mmenu : AppCompatActivity() {



        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.pocetnakazina)

           udjiupoker()
            udjiuslot()
            ugasiigru()
        }


        fun udjiupoker(){
            texasholdem.setOnClickListener {
                val intent = Intent(this, PokerMain::class.java)
                startActivity(intent)
            }
        }

    fun udjiuslot(){
        vecamanja.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

        fun ugasiigru(){
            izadji.setOnClickListener { exitProcess(0); }
        }

}



