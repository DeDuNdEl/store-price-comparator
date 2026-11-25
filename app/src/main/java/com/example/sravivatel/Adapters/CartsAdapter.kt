package com.example.sravivatel.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import com.example.sravivatel.CartActivity
import com.example.sravivatel.Classes.DBHelper
import com.example.sravivatel.Other.MyApplication
import com.example.sravivatel.R


class CartsAdapter(var name: ArrayList<String>, var context: Context) : RecyclerView.Adapter<CartsAdapter.MyViewHolder>() {



    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        val remove: Button = view.findViewById(R.id.remove_cart_button)
        val button: Button = view.findViewById(R.id.product_in_cart_name)
        val current_cart: CheckBox = view.findViewById(R.id.checkBox_current_cart)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_in_list, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return name.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.button.text = name[position]

        val db = DBHelper(context, null)



        holder.remove.setOnClickListener {

            if (MyApplication().getCurrentCart() == name[position]){
                MyApplication().setCurrentCart("корзина не выбрана")
            }

            db.removeCart(name[position])
            name.removeAt(position)
            notifyDataSetChanged()


        }

        holder.button.setOnClickListener(){
            val intent = Intent(context, CartActivity::class.java)

            intent.putExtra("product_in_cart_name", name[position])
            MyApplication().setCurrentCart_Tech(name[position])

            context.startActivity(intent)
        }

        if (MyApplication().getCurrentCart() == name[position]){
            holder.current_cart.setChecked(true)
        }

        holder.current_cart.setOnClickListener(){

            if (MyApplication().getCurrentCart() != name[position] && MyApplication().getCurrentCart() != "корзина не выбрана"){

                notifyDataSetChanged()
                holder.current_cart.setChecked(false)
                Toast.makeText(context, "Может быть выбрана только одна корзина", Toast.LENGTH_SHORT).show()

            }

            if (MyApplication().getCurrentCart() == name[position]){
                MyApplication().setCurrentCart("корзина не выбрана")
            }

            else if (MyApplication().getCurrentCart() == "корзина не выбрана") {
                MyApplication().setCurrentCart(name[position])
            }

        }


    }


}



