package com.example.erol.testerol1

import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = "MainActivity"
    }

    val vmGame = ViewModelGame()
    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "onCreate")
        buttonHi.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                vmGame.playNewCard(false)
            }
        })
        buttonLow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                vmGame.playNewCard(true)
            }
        })

        startGame()
        vmGame.startGame()
        Log.i(TAG, "onCreate - finished")

        poker.setOnClickListener({
            val intent = Intent(this, PokerMain::class.java)
            startActivity(intent)
        })

    }

    fun getDrawableForCard(card: Card): Drawable? {
        val id = resources.getIdentifier("card_${card.number}_${card.pledge}", "drawable", packageName)
        return getDrawable(id)
    }

    fun startGame() {

        vmGame.cash.observe(this, android.arch.lifecycle.Observer {
            it?.let {
                tvMoney.text = it.toString()
                if (it == 0) {
                    showDialog()
                }
            }
        })


        vmGame.currentCard.observe(this, android.arch.lifecycle.Observer {
            it?.let {
                counter +=1
                val imageTemp = ImageView(this)
                val params = FrameLayout.LayoutParams( 240, 350)
                params.marginStart = 20 + counter * 20
                imageTemp.setImageDrawable(getDrawableForCard(it))
                layImages.addView(imageTemp,params)

            }
        })
    }

    fun showDialog(){
        val builder = AlertDialog.Builder(this)
        val possitiveButtonClick = { dialog: DialogInterface, which: Int ->
            vmGame.startGame()
//            layImages.removeAllViews()
            layImages.removeAllViewsInLayout()
        }
        val negativeButtonClick = { dialog: DialogInterface, which: Int ->
            finish()
        }
        builder.setTitle("Dobrodosao Bane")
        builder.setMessage("Dodji da se igramo..")
        builder.setPositiveButton("Normalno da ocu imam viska para", DialogInterface.OnClickListener(function = possitiveButtonClick))
        builder.setNegativeButton("Nemerem vise", DialogInterface.OnClickListener(function = negativeButtonClick))
        builder.setCancelable(false)
        builder.show()
    }

}

