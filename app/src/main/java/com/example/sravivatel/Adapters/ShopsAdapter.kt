package com.example.sravivatel.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sravivatel.Classes.DBHelper
import com.example.sravivatel.Classes.Shop
import com.example.sravivatel.R

class ShopsAdapter(var shops: ArrayList<Shop>, var context: Context, val currentCart: String?) : RecyclerView.Adapter<ShopsAdapter.MyViewHolder>() {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){


        val logo: ImageView = view.findViewById(R.id.shop_image)
        val price: TextView = view.findViewById(R.id.category_image)
        val amount_products: TextView = view.findViewById(R.id.num_of_products)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shop_in_list, parent, false)
        return MyViewHolder(view)
    }


    override fun getItemCount(): Int {
        return shops.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val db = DBHelper(context, null)

        val imageId = context.resources.getIdentifier(shops[position].logo, "drawable", context.packageName)

        holder.price.text = shops[position].price.toString() +  "р"

        holder.amount_products.text =  shops[position].num_of_products.toString() + "/" + (db.getTableData(currentCart).size).toString()

        holder.logo.setImageResource(imageId)


        }


}




