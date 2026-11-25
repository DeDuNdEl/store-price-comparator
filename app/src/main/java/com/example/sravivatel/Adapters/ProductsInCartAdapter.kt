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
import com.example.sravivatel.CartActivity
import com.example.sravivatel.Classes.DBHelper
import com.example.sravivatel.Classes.ProductInCart
import com.example.sravivatel.Other.MyApplication
import com.example.sravivatel.ProductInfoActivity
import com.example.sravivatel.R
import com.example.sravivatel.SearchFragment
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso

class ProductsInCartAdapter(var products: ArrayList<ProductInCart>, var context: Context, val currentCart: String?) : RecyclerView.Adapter<ProductsInCartAdapter.MyViewHolder>() {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        val name: TextView = view.findViewById(R.id.product_in_cart_name)
        val image: ImageView = view.findViewById(R.id.product_in_cart_image)
        val cost: TextView = view.findViewById(R.id.product_in_cart_cost)
        val logo: ImageView = view.findViewById(R.id.product_in_cart_logo)
        val weight: TextView = view.findViewById(R.id.product_in_cart_weight)
        val amount_shops: TextView = view.findViewById(R.id.product_in_cart_amount_shops)
        val minus_amount: ImageButton = view.findViewById(R.id.product_in_cart_amount_minus)
        val plus_amount: ImageButton = view.findViewById(R.id.product_in_cart_amount_plus)
        val text_amount: TextView = view.findViewById(R.id.product_in_cart_amount)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_in_cart, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var prices = arrayListOf<Long>()
        var logos = arrayListOf<String>()

        if (products[position].cost_paterocka != 0L){
            prices.add(products[position].cost_paterocka)
            logos.add("paterocka")
        }

        if (products[position].cost_diksi != 0L){
            prices.add(products[position].cost_diksi)
            logos.add("diksi")
        }

        if (products[position].cost_magnit_celevaya != 0L){
            prices.add(products[position].cost_magnit_celevaya)
            logos.add("magnit_celevaya")
        }

        if (products[position].cost_perekrestok != 0L){
            prices.add(products[position].cost_perekrestok)
            logos.add("perekrestok")
        }

        val min_cost = prices.min()
        val imageId = context.resources.getIdentifier(logos[prices.indexOf(min_cost)], "drawable", context.packageName)

        holder.name.text = products[position].name
        holder.cost.text = min_cost.toString() +  "р"
        holder.weight.text = products[position].weight

        Picasso.get().load("https://avatars.mds.yandex.net/get-eda/"+products[position].img_url+"/400x400nocrop").into(holder.image)
        holder.logo.setImageResource(imageId)
        holder.amount_shops.text = prices.size.toString() + "/4"

        val db = DBHelper(context, null)

        holder.text_amount.setText(db.getValuesFromCart(currentCart, products[position].img_url).toString())

        holder.plus_amount.setOnClickListener{


            val new_amount = db.getValuesFromCart(currentCart, products[position].img_url) + 1
            db.updateValuesFromCart(currentCart, new_amount, products[position].img_url)
            holder.text_amount.setText(new_amount.toString())
            if (currentCart != null) {
                (context as CartActivity).ShopPrices(currentCart)
            }

        }

        holder.minus_amount.setOnClickListener{


            val new_amount = db.getValuesFromCart(currentCart, products[position].img_url) - 1

            if (new_amount <= 0){

                db.deleteValuesFromCart(currentCart, products[position].img_url)
                products.removeAt(position)
                notifyDataSetChanged()
            }
            else{
                db.updateValuesFromCart(currentCart, new_amount, products[position].img_url)
                holder.text_amount.setText(new_amount.toString())
            }

            if (currentCart != null) {
                (context as CartActivity).ShopPrices(currentCart)
            }


        }

        holder.name.setOnClickListener {
            val intent = Intent(context, ProductInfoActivity::class.java)

            intent.putExtra("product_in_list_name", products[position].name)
            intent.putExtra("product_in_list_weight", products[position].weight)
            intent.putExtra("url", products[position].img_url)

            intent.putExtra("paterocka", products[position].cost_paterocka)
            intent.putExtra("diksi", products[position].cost_diksi)
            intent.putExtra("magnit_celevaya", products[position].cost_magnit_celevaya)
            intent.putExtra("perekrestok", products[position].cost_perekrestok)
            //intent.putExtra("product_in_list_image", )

            context.startActivity(intent)

        }



    }
}



