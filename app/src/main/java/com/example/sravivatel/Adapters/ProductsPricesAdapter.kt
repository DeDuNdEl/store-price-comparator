package com.example.sravivatel.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sravivatel.Classes.Price
import com.example.sravivatel.Classes.Product
import com.example.sravivatel.ProductInfoActivity
import com.example.sravivatel.R
import com.squareup.picasso.Picasso

class ProductsPricesAdapter(var products: List<Price>, var context: Context) : RecyclerView.Adapter<ProductsPricesAdapter.MyViewHolder>() {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        val text: TextView = view.findViewById(R.id.product_price_text)
        val logo: ImageView = view.findViewById(R.id.product_price_logo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_price, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val imageId = context.resources.getIdentifier(products[position].logo, "drawable", context.packageName)

        if (products[position].cost == 0L){
            holder.text.text = products[position].shop + " - данных нет"
        }
        else {
            holder.text.text = products[position].shop + " - " + products[position].cost + " р"
        }

        holder.logo.setImageResource(imageId)



    }
}



