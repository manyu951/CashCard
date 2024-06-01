package com.manyu.cashcard


import android.content.Intent
import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import androidx.appcompat.app.AppCompatActivity
import com.manyu.cashcard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonProfile.setOnClickListener {
            openProfile()
        }
        binding.buttonSell.setOnClickListener {
            openSell()
        }

        binding.buttonAmazon.setOnClickListener {
            openAmazon()
        }
        binding.buttonFlipkart.setOnClickListener {
            openFlipkart()
        }

    }



    private fun openProfile() {
        val intent = Intent(this, profile::class.java)
        startActivity(intent)
    }
    private fun openSell() {
        val intent = Intent(this, Sell::class.java)
        startActivity(intent)
    }

//    private fun openAmazon() {
//        var i = Intent(this,cardList::class.java)
//        startActivity(i)
//
//        intent.putExtra("buttonText", "Amazon")
//
//        finish()
//    }
    private fun openAmazon() {
        val intent = Intent(this, cardList::class.java)
        intent.putExtra("buttonText", "Amazon") // Set the extra here
        startActivity(intent)
        finish()
    }
    private fun openFlipkart() {
        val intent = Intent(this, cardList::class.java)
        intent.putExtra("buttonText", "Flipkart") // Set the extra here
        startActivity(intent)
        finish()
    }


}