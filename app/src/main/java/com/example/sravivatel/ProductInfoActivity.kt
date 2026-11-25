package com.example.sravivatel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sravivatel.Adapters.ProductsPricesAdapter
import com.example.sravivatel.Classes.Price
import com.squareup.picasso.Picasso

class ProductInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_info)

        fun sortKey(e: List<Any>): Long {
            return e[1] as Long
        }

        val name: TextView = findViewById(R.id.product_in_list_name)
        val weight: TextView = findViewById(R.id.product_in_list_weight)
        val image: ImageView = findViewById(R.id.category_image)

        name.text = intent.getStringExtra("product_in_list_name")
        weight.text = intent.getStringExtra("product_in_list_weight")

        Picasso.get().load("https://avatars.mds.yandex.net/get-eda/"+ intent.getStringExtra("url")+"/400x400nocrop").into(image)

        var recyclerView: RecyclerView
        var adapter: ProductsPricesAdapter

        var pricesClass: ArrayList<Price>

        var prices = mutableListOf<Long>(intent.getLongExtra("paterocka", 0), intent.getLongExtra("diksi", 0), intent.getLongExtra("magnit_celevaya", 0), intent.getLongExtra("perekrestok", 0))
        prices = prices.sorted().toMutableList()

        var priceList = listOf(
            listOf("Дикси", intent.getLongExtra("diksi", 0), "diksi"),
            listOf("Пятерочка", intent.getLongExtra("paterocka", 0), "paterocka"),
            listOf("Магнит", intent.getLongExtra("magnit_celevaya", 0), "magnit_celevaya"),
            listOf("Перекресток", intent.getLongExtra("perekrestok", 0), "perekrestok")
        )

        priceList = priceList.sortedBy { sortKey(it) }

        pricesClass = arrayListOf<Price>(
            Price(priceList[0][0] as String, priceList[0][1] as Long, priceList[0][2] as String),
            Price(priceList[1][0] as String, priceList[1][1] as Long, priceList[1][2] as String),
            Price(priceList[2][0] as String, priceList[2][1] as Long, priceList[2][2] as String),
            Price(priceList[3][0] as String, priceList[3][1] as Long, priceList[3][2] as String)
        )

        recyclerView = findViewById(R.id.product_info_recyclerView_prices)
        recyclerView = findViewById(R.id.product_info_recyclerView_prices)
        adapter = ProductsPricesAdapter(pricesClass, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 1)
    }
}