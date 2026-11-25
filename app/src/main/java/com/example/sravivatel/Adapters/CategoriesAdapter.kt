package com.example.sravivatel.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sravivatel.Classes.Category
import com.example.sravivatel.R
import com.example.sravivatel.SearchFragment
import com.squareup.picasso.Picasso

class CategoriesAdapter(var category: ArrayList<Category>, var context: SearchFragment, val view: View) : RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>() {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){


        val image: ImageView = view.findViewById(R.id.category_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return category.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Picasso.get().load(category[position].url).into(holder.image)

        holder.image.setOnClickListener {
            (context as SearchFragment).clickOnCategory(view, category[position].id.toLong())
        }
    }


}




