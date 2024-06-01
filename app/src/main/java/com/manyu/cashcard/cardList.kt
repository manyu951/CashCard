package com.manyu.cashcard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class cardList : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<SellData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_list)

        userRecyclerView = findViewById(R.id.recycler_view)//recycle_view in card_list
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
//        getUserData()

//        val bundle: Bundle? = intent.extras

        val buttonText = intent.getStringExtra(/* name = */ "buttonText")
        Toast.makeText(this, "$buttonText", Toast.LENGTH_SHORT).show()
        if (buttonText == "Amazon"){
            databaseReference = FirebaseDatabase.getInstance().getReference("Amazon")
        }
        else if(buttonText == "Flipkart"){
            databaseReference = FirebaseDatabase.getInstance().getReference("Flipkart")
        }




        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(SellData::class.java)
                        userArrayList.add(user!!)
                    }
                    userRecyclerView.adapter = MyAdapter(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

//    private fun getUserData(){
//        databaseReference = FirebaseDatabase.getInstance().getReference("users")
//        databaseReference.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot.exists()){
//                    for(userSnapshot in snapshot.children){
//                        val user = userSnapshot.getValue(SellData::class.java)
//                        userArrayList.add(user!!)
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
//    }
}