package com.manyu.cashcard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Sell : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell)

        firebaseAuth = FirebaseAuth.getInstance()
//        databaseReference = FirebaseDatabase.getInstance().getReference("Amazon")

        auth = FirebaseAuth.getInstance()

        val spinnerList: Spinner = findViewById(R.id.spinnerList)
        val cardNumber: EditText = findViewById(R.id.CardNumber)
        val cardCode: EditText = findViewById(R.id.CardCode)
        val datePicker: DatePicker = findViewById(R.id.datePicker)
        val buttonSell: Button = findViewById(R.id.button_Sell)



        spinnerList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Get the selected item from the Spinner
                val selectedItem = parent?.getItemAtPosition(position).toString()

                // Hide the EditText based on the selected item
                if (selectedItem == "AMAZON") {
                    databaseReference = FirebaseDatabase.getInstance().getReference("Amazon")
                    cardCode.visibility = View.GONE
                } else {
                    databaseReference = FirebaseDatabase.getInstance().getReference("Flipkart")
                    cardCode.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case when nothing is selected
            }
        }

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.CashCard,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerList.adapter = adapter

        buttonSell.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage("Are you sure you want to sell now?")

        builder.setPositiveButton("Yes") { dialog, _ ->
            saveDataToFirebase()
            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun saveDataToFirebase() {

        val spinner = findViewById<Spinner>(R.id.spinnerList).selectedItem.toString()
        val cardNumber = findViewById<EditText>(R.id.CardNumber).text.toString().trim()
        val cardCode = findViewById<EditText>(R.id.CardCode).text.toString().trim()
        val sellingPrice = findViewById<EditText>(R.id.sellingPrice).text.toString().trim()
        val actualPrice = findViewById<EditText>(R.id.actualPrice).text.toString().trim()
        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val day = datePicker.dayOfMonth
        val month = datePicker.month + 1
        val year = datePicker.year
        val selectedDate = "$day/$month/$year"




        if (sellingPrice.isEmpty() && actualPrice.isEmpty() && cardNumber.isEmpty() || cardCode.isEmpty() && spinner != "AMAZON" ) {
            Toast.makeText(this, "Please enter card number and code", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = firebaseAuth.currentUser?.uid ?: ""

//        data class User(val spinner: String,
//                        val cardNumber: String,
//                        val cardCode: String,
//                        val ExpDate:String,
//                        val sellingPrice:String,
//                        val actualPrice:String,
//            )


        val sellingdata = SellData(userId,spinner, cardNumber, cardCode,selectedDate,sellingPrice,actualPrice)
        val randomValue = (1000000..9999999999).random()

        databaseReference.child("$randomValue+$cardNumber").setValue(sellingdata)
            .addOnSuccessListener {
                Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
