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
import com.example.sravivatel.Classes.DBHelper
import com.example.sravivatel.Classes.Product
import com.example.sravivatel.Other.MyApplication
import com.example.sravivatel.ProductInfoActivity
import com.example.sravivatel.R
import com.squareup.picasso.Picasso

class ProductsAdapter(var products: List<Product>, var context: Context) : RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        val name: TextView = view.findViewById(R.id.product_in_list_name)
        val image: ImageButton = view.findViewById(R.id.category_image)
        val cost: TextView = view.findViewById(R.id.product_in_list_cost)
        val logo: ImageView = view.findViewById(R.id.product_in_list_logo)
        val weight: TextView = view.findViewById(R.id.product_in_list_weight)
        val button_add_to_carts: Button = view.findViewById(R.id.product_in_list_add_to_cart)
        val amount_shops: TextView = view.findViewById(R.id.product_in_list_amount_shops)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var prices = arrayListOf<Long>()
        var logos = arrayListOf<String>()


        if (products[position].cost_diksi != 0L){
            prices.add(products[position].cost_diksi)
            logos.add("diksi")
        }

        if (products[position].cost_paterocka != 0L){
            prices.add(products[position].cost_paterocka)
            logos.add("paterocka")
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
        holder.cost.text = "от " + min_cost.toString()+  "р"
        holder.weight.text = products[position].weight

        Picasso.get().load("https://avatars.mds.yandex.net/get-eda/"+products[position].img_url+"/400x400nocrop").into(holder.image)
        holder.logo.setImageResource(imageId)
        holder.amount_shops.text = prices.size.toString() + "/4"

        holder.button_add_to_carts.setOnClickListener {


            val db = DBHelper(context, null)
            var products_urls = ArrayList<String>()
            val now_cart = MyApplication().getCurrentCart()

            if (MyApplication().getCurrentCart() == "корзина не выбрана"){
                Toast.makeText(context, """выберите корзину во вкладке "корзины" """, Toast.LENGTH_SHORT).show()
            }
            else{
                for (i in db.getTableData(MyApplication().getCurrentCart())){
                    products_urls.add(i[0])
                }
                if ( products[position].img_url in products_urls){
                    Toast.makeText(context, """продукт уже в корзине""", Toast.LENGTH_SHORT).show()
                }
                else{

                    db.addValuesToCart(MyApplication().getCurrentCart(), products[position].img_url, 1)
                    Toast.makeText(context, """добавлено в $now_cart""", Toast.LENGTH_SHORT).show()
                }

            }



        }

        holder.image.setOnClickListener {
            val intent = Intent(context, ProductInfoActivity::class.java)

            intent.putExtra("product_in_list_name", products[position].name)
            intent.putExtra("product_in_list_weight", products[position].weight)
            intent.putExtra("url", products[position].img_url)

            intent.putExtra("diksi", products[position].cost_diksi)
            intent.putExtra("paterocka", products[position].cost_paterocka)
            intent.putExtra("magnit_celevaya", products[position].cost_magnit_celevaya)
            intent.putExtra("perekrestok", products[position].cost_perekrestok)
            //intent.putExtra("product_in_list_image", )

            context.startActivity(intent)

        }



    }
}



