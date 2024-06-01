package com.manyu.cashcard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val userList:ArrayList<SellData>): RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.items, parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val currentitem = userList[position]
        holder.pricefire.text = userList[position].sellingPrice
        holder.valuefire.text = userList[position].actualPrice
        holder.expiryfire.text = userList[position].expDate

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val pricefire : TextView = itemView.findViewById(R.id.pricefire)
        val valuefire : TextView = itemView.findViewById(R.id.valuefire)
        val expiryfire : TextView = itemView.findViewById(R.id.expiryfire)
    }
}