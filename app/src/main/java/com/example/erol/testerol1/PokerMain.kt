package com.example.erol.testerol1

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.poker_main.*
import java.util.ArrayList
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.DialogInterface
import android.nfc.Tag
import android.support.v7.app.AlertDialog
import android.widget.Button
import java.lang.invoke.ConstantCallSite
import kotlin.math.min
import android.support.v7.widget.AppCompatEditText

class PokerMain : AppCompatActivity() {

    companion object {

        private val TAG = "PokerMain"
    }

    val vmGame = ViewModelGamePoker()
    var counter = 0
    val map = HashMap<Int, Card?>()


    var pare1 = Constants.defaultCash
    var brojac = 0
    var bet = 100
    val naziv = "Bet: "
    val parenaziv = "Pare: "
    var zaradiosi= "Dob si:"
    var dobitnost= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.poker_main)
        zarada.text=zaradiosi + dobitnost
        changeActivity()
        dealCard()
        vmGame.startGame()
        startCash()
        minmaxbet()
        kasa.text = pare1.toString()
        betxt.text = bet.toString()
        counter1()


    }

    fun startCash() {
        map[R.id.card1] = null
        map[R.id.card2] = null
        map[R.id.card3] = null
        map[R.id.card4] = null
        map[R.id.card5] = null


    }

    fun dealCard() {

        deal.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                if ((pare1 < bet) && (brojac == 0)) {
                    greska()
                } else {
                    brojac++
                    vmGame.deal(map)

                    drawCards()

                    card1.setOnClickListener {
                        counter += 1
                        onCardClicked(it.id)
                    }

                    card2.setOnClickListener {
                        counter += 1
                        onCardClicked(it.id)
                    }

                    card3.setOnClickListener {
                        counter += 1
                        onCardClicked(it.id)
                    }

                    card4.setOnClickListener {
                        onCardClicked(it.id)
                        counter += 1
                    }

                    card5.setOnClickListener {
                        onCardClicked(it.id)
                        counter += 1

                    }
                    counter1()
                   /* trenutno.text = brojac.toString()*/
                    pokreniponovo()


                }
            }
        })

    }

    fun drawCards() {

        map.keys.forEach { key ->
            map[key]?.let {
                val imgView = findViewById<ImageView>(key)
                if (it.hidden) {
                    imgView?.setImageResource(R.drawable.pozadina)

                } else {

                    imgView?.setImageDrawable(getDrawableForCard(it))
                }


            }
        }
    }

    fun changeActivity() {
        meni2.setOnClickListener() {
            val intent = Intent(this, Mmenu::class.java)
            startActivity(intent)

        }
        }

    fun getDrawableForCard(card: Card): Drawable? {
        Log.i(TAG, "drawable ${card.number}_${card.pledge}")
        val id = resources.getIdentifier("card_${card.number}_${card.pledge}", "drawable", packageName)
        return getDrawable(id)
    }

    fun onCardClicked(idCard: Int) {

        val card = map[idCard]
        card?.toogle()
        drawCards()
    }

    fun counter1() {
        var tekstic = "Imas "
        var tekstic1 = " para"
        kasa.text = tekstic + pare1.toString() + tekstic1
       // trenutno.text = brojac.toString()

        if (brojac == 1) {

            pare1 = pare1 - bet
            var tekstic = "Imas "
            var tekstic1 = " para"
            kasa.text = tekstic + pare1.toString() + tekstic1
            //trenutno.text = brojac.toString()
        }

        if (brojac == 2) {
            threeorfour() //par triling i poker
            boja() //boja
            twopair() //dva i full
            straight() // skala,boja i boja u skali

            brojac = 0

            startCash()
        }


        //trenutno.text = brojac.toString()

        if ((pare1 == 0) && (brojac == 0)) {
            alertpokreniponovo()
        }
    }


    fun pair() {
        if (map[R.id.card1]?.number == map[R.id.card2]?.number || map[R.id.card1]?.number == map[R.id.card3]?.number
                || map[R.id.card1]?.number == map[R.id.card4]?.number || map[R.id.card1]?.number == map[R.id.card5]?.number
                || map[R.id.card2]?.number == map[R.id.card3]?.number || map[R.id.card2]?.number == map[R.id.card4]?.number
                || map[R.id.card2]?.number == map[R.id.card5]?.number || map[R.id.card3]?.number == map[R.id.card4]?.number
                || map[R.id.card3]?.number == map[R.id.card5]?.number || map[R.id.card4]?.number == map[R.id.card5]?.number) {
            bet = bet * 2
            pare1 = pare1 + bet
            kasa.text = pare1.toString()
            bet = bet / 2
            dobitak()           //dodaje pare na racun zatim vrednost beta vraca u normalu
        } else {
            if ((pare1 == 0) && (brojac == 2)) {
                alertpokreniponovo()
            } else {
                nemanista()
            }
        }
    }


    fun threeorfour() {     //par, triling, poker
        var maxDob = 0
        map.keys.forEach { key ->
            map[key]?.let {
                val rez = threeorfourdodatak(it.number)
                if (rez > maxDob) {
                    maxDob = rez
                }
            }

        }
        Log.i(TAG, "threeorfour = $maxDob")
        if ((maxDob == 2) && (brojac == 2)) {
            bet = bet * 1
            pare1 = pare1 + bet
            kasa.text = pare1.toString()
            dobitnost=bet.toString()
            bet = bet / 1
            dobitak()
        }

        if ((maxDob == 3) && (brojac == 2)) {
            bet = bet * 3
            pare1 = pare1 + bet
            kasa.text = pare1.toString()
            dobitnost=bet.toString()
            bet = bet / 3
            dobitak()
        }

        if ((maxDob == 4) && (brojac == 2)) {
            bet = bet * 7
            pare1 = pare1 + bet
            kasa.text = pare1.toString()
            dobitnost=bet.toString()
            bet = bet / 7
            dobitak()
        }

    }


    fun boja() {
        var maxDob = 0
        map.keys.forEach { key ->
            map[key]?.let {
                val rez = boja22dodadtak(it.pledge)
                if (rez > maxDob) {
                    maxDob = rez


                    if ((brojac == 2) && (maxDob == 5)) {
                        bet = bet * 5
                        pare1 = pare1 + bet
                        kasa.text = pare1.toString()
                        dobitnost=bet.toString()
                        bet = bet / 5
                        dobitak()

                    }
                }
            }
        }
        Log.i(TAG, "imam toloiko pledga na boji = $maxDob, bet je $bet")
    }

    fun twopair() {     //dva para i fullhaus
        var maxDob = 0
        var dvapara = 0
        map.keys.forEach { key ->
            map[key]?.let {
                var rez = twopairdodatak(it.number)
                if (rez > maxDob) {
                    maxDob = rez

                }
                if ((rez >= 2) && (rez <= 3)) {
                    dvapara += rez

                }

            }
        }
        if ((brojac == 2) && (dvapara == 8)) {
            bet = bet * 2
            pare1 = pare1 + bet
            kasa.text = pare1.toString()
            dobitnost = bet.toString()
            bet = bet / 2
            dobitak()
        }
        if ((brojac == 2) && (dvapara == 13)) {
            bet = bet * 6
            pare1 = pare1 + bet
            kasa.text = pare1.toString()
            dobitnost=bet.toString()
            bet = bet / 6
            dobitak()
        }

        Log.i(TAG, "Dva i fullhaus = $dvapara")
    }


    fun straight() {
        var maxDob = 0
        val alist = arrayListOf<Int>(map[R.id.card1]!!.number, map[R.id.card2]!!.number, map[R.id.card3]!!.number, map[R.id.card4]!!.number, map[R.id.card5]!!.number)
        alist.sort()
        var prvi = alist[0]
        var drugi = alist[1]
        var treci = alist[2]
        var cetvrti = alist[3]
        var peti = alist[4]
        var provera = prvi + drugi + treci + cetvrti + peti
        map.keys.forEach { key ->
            map[key]?.let {
                val rez = boja22dodadtak(it.pledge)
                if (rez > maxDob) {
                    maxDob = rez
                }
            }
        }

        if ((prvi + 1 == drugi) && (drugi + 1 == treci) && (treci + 1 == cetvrti) && (cetvrti + 1 == peti)) {

            if ((brojac == 2) && (maxDob == 5)) {
                bet = bet * 8
                pare1 = pare1 + bet
                kasa.text = pare1.toString()
                dobitnost=bet.toString()
                bet = bet / 8
                dobitak()
            } else {
                bet = bet * 4
                pare1 = pare1 + bet
                kasa.text = pare1.toString()
                dobitnost=bet.toString()
                bet = bet / 4
                dobitak()           //skala u boji i skala
            }
        }
        if ((prvi == 1) && (drugi == 10) && (treci == 12) && (cetvrti == 13) && (peti == 14) && (maxDob == 5)) {
            bet = bet * 9
            pare1 = pare1 + bet
            kasa.text = pare1.toString()
            dobitnost=bet.toString()
            bet = bet / 9
            dobitak()
        }
        if (((prvi == 1) && (drugi == 10) && (treci == 12) && (cetvrti == 13) && (peti == 14)) ||
                ((prvi == 1) && (drugi == 7) && (treci == 8) && (cetvrti == 9) && (peti == 10)) ||
                ((prvi == 1) && (drugi == 8) && (treci == 9) && (cetvrti == 10) && (peti == 12)) ||
                ((prvi == 1) && (drugi == 9) && (treci == 10) && (cetvrti == 12) && (peti == 13))) {
            bet = bet * 4
            pare1 = pare1 + bet
            kasa.text = pare1.toString()
            dobitnost=bet.toString()
            bet = bet / 4
            dobitak()
        }


        Log.i(TAG, "lista $alist, dobitak $maxDob, bet je $bet")
    }


    //dopuna funkcija
    fun boja22dodadtak(card: String): Int {
        var rezultat = 0;
        map.keys.forEach { key ->
            map[key]?.let {
                if (it.pledge == card) {
                    rezultat += 1
                }
            }

        }
        return rezultat
    }

    fun bojadodadtak(card: String): Int {
        var rezultat = 0;
        map.keys.forEach { key ->
            map[key]?.let {
                if (it.pledge == card) {
                    rezultat += 1
                }
            }

        }
        return rezultat
    }

    fun threeorfourdodatak(card: Int): Int {
        var rezultat = 0;
        map.keys.forEach { key ->
            map[key]?.let {
                if (it.number == card) {
                    rezultat += 1
                }
            }

        }
        return rezultat

    }

    fun twopairdodatak(card: Int): Int {
        var rezultat = 0;
        map.keys.forEach { key ->
            map[key]?.let {
                if (it.number == card) {
                    rezultat += 1
                }
            }

        }
        return rezultat
    }

    fun dobitak() {
        val builder = AlertDialog.Builder(this)
        val possitiveButtonClick = { dialog: DialogInterface, which: Int ->
            vmGame.startGame()

        }
        val negativeButtonClick = { dialog: DialogInterface, which: Int ->
            finish()
        }
        builder.setTitle("Cestitamo")
        builder.setMessage("Dobio si ")
        builder.setPositiveButton("Hocu jos", DialogInterface.OnClickListener(function = possitiveButtonClick))
        builder.setCancelable(false)
        builder.show()
    }

    fun greska() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Bet ne moze biti manji od 0 kasa ne moze biti manja od beta")
        builder.show()
    }

    fun nemanista() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Nisi nista dobio")
        val possitiveButtonClick = { dialog: DialogInterface, which: Int -> vmGame.startGame() }
        builder.setPositiveButton("Hocu jos", DialogInterface.OnClickListener(function = possitiveButtonClick))
        builder.show()
    }

    fun pokreniponovo() {
        if ((brojac == 2) && (pare1 == 0)) {
            alertpokreniponovo()
            pare1 = Constants.defaultCash
        }
    }

    fun ponovo() {
        pare1 = Constants.defaultCash
        kasa.text = parenaziv + pare1.toString()

    }

    fun alertpokreniponovo() {

        val builder = AlertDialog.Builder(this)
        val possitiveButtonClick = { dialog: DialogInterface, which: Int ->
            ponovo()

        }
        val negativeButtonClick = { dialog: DialogInterface, which: Int ->
            finish()
        }
        builder.setTitle("Nazalost nemas vise novca u kasi")
        builder.setMessage("Da li zelis da se igras jos")
        builder.setPositiveButton("Hocu jos", DialogInterface.OnClickListener(function = possitiveButtonClick))
        builder.setNegativeButton("Necu vise", DialogInterface.OnClickListener(function = negativeButtonClick))
        builder.setCancelable(false)
        builder.show()

    }

    fun minmaxbet() {
        if (brojac == 0) {
            raiseBet.setOnClickListener { raise() }
            minBet.setOnClickListener { lower() }
        }
    }

    fun raise() {
        if (brojac == 0) {
            if (bet >= 1000) {
                bet = bet + 250
            }
            if ((bet < 999) && (bet >= 500)) {
                bet = bet + 100
            }

            if ((bet < 499) && (bet >= 100)) {
                bet = bet + 50
            }
            if (bet < 101) {
                bet = bet + 10
            }
        }
        betxt.text = naziv + bet.toString()

    }

    fun lower() {

        if (brojac == 0) {
            if (bet == 0) {
                greska()
            } else {

                if ((bet <= 100) && (bet > 0)) {
                    bet = bet - 10
                }

                if ((bet <= 500) && (bet >= 101)) {
                    bet = bet - 50
                }
                if ((bet <= 1000) && (bet > 499)) {
                    bet = bet - 100
                }
                if (bet >= 1000) {
                    bet = bet - 250
                }

                betxt.text = naziv + bet.toString()
            }
        }
    }
}


