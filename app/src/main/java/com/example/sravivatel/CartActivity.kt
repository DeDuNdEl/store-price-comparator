package com.example.sravivatel

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sravivatel.Adapters.ProductsInCartAdapter
import com.example.sravivatel.Adapters.ShopsAdapter
import com.example.sravivatel.Classes.DBHelper
import com.example.sravivatel.Classes.Product
import com.example.sravivatel.Classes.ProductInCart
import com.example.sravivatel.Classes.Shop
import com.example.sravivatel.Other.MyApplication
import java.text.SimpleDateFormat
import java.util.Date


private lateinit var adapterProducts: ProductsInCartAdapter
private lateinit var adapterShops: ShopsAdapter

private lateinit var recyclerViewProducts: RecyclerView
private lateinit var recyclerViewShops: RecyclerView

private lateinit var productsInCart: ArrayList<ProductInCart>
private lateinit var products: ArrayList<Product>



class CartActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    fun ShopPrices(name_text: String){

        var shops: ArrayList<Shop>  = ArrayList<Shop>()
        val db = DBHelper(this, null)

        val product_cart_urls = ArrayList<String>()

        var prices =  Array<Long>(4) {0}
        var counts =  Array<Long>(4) {0}
        var logos =  arrayListOf<String>("diksi", "paterocka", "magnit_celevaya", "perekrestok")

        for (i in db.getTableData(name_text)) {
            product_cart_urls.add(i[0])
        }




        for (i in db.getTableData("local_products_DB")) {

            val diksi = i[1]
            val paterocka = i[2]
            val magnit_celevaya = i[3]
            val perekrestok = i[4]

            val img_url = i[6]



            if (img_url in product_cart_urls) {


                prices[0] += diksi.toLong() * db.getValuesFromCart(name_text, img_url).toLong()
                if (diksi.toLong() != 0L){ counts[0] += 1L }

                prices[1] += paterocka.toLong() * db.getValuesFromCart(name_text, img_url).toLong()
                if (paterocka.toLong() != 0L){ counts[1] += 1L }

                prices[2] += magnit_celevaya.toLong() * db.getValuesFromCart(name_text, img_url).toLong()
                if (magnit_celevaya.toLong() != 0L){ counts[2] += 1L }

                prices[3] += perekrestok.toLong() * db.getValuesFromCart(name_text, img_url).toLong()
                if (perekrestok.toLong() != 0L){ counts[3] += 1L }

            }
        }

        for (i in 0..3) {
            shops.add(Shop(logos[i], prices[i], counts[i]))
        }

        recyclerViewShops = findViewById(R.id.shops_in_list)
        adapterShops = ShopsAdapter(shops, this, name_text)
        recyclerViewShops.adapter = adapterShops

        val horizontalLayoutManagaer = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewShops.setLayoutManager(horizontalLayoutManagaer)


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_activity)

        val name: TextView = findViewById(R.id.cart_name)
        val change_name: Button = findViewById(R.id.button_change_cart_name)
        val name_text: String? = intent.getStringExtra("product_in_cart_name")

        val db = DBHelper(this, null)
        val product_cart_urls = ArrayList<String>()
        var prices =  Array<Long>(4) {0}
        var counts =  Array<Long>(4) {0}
        var logos =  arrayListOf<String>("diksi", "paterocka", "magnit_celevaya", "perekrestok")
        val currentCart = MyApplication().getCurrentCart_Tech()

        name.text = name_text
        productsInCart = ArrayList<ProductInCart>()

        for (i in db.getTableData(name_text)) {
            product_cart_urls.add(i[0])
        }


        products = ArrayList<Product>()
        productsInCart = ArrayList<ProductInCart>()
        var shops: ArrayList<Shop>  = ArrayList<Shop>()


        for (i in db.getTableData("local_products_DB")) {
            val name = i[0]
            val diksi = i[1]
            val paterocka = i[2]
            val magnit_celevaya = i[3]
            val perekrestok = i[4]
            val weight = i[5]
            val img_url = i[6]
            val category = i[7]

            products.add(
                Product(
                    name,
                    diksi.toLong(),
                    paterocka.toLong(),
                    magnit_celevaya.toLong(),
                    perekrestok.toLong(),
                    weight,
                    img_url,
                    category.toLong()
                )
            )

            if (img_url in product_cart_urls) {

                productsInCart.add(
                    ProductInCart(
                        name,
                        diksi.toLong(),
                        paterocka.toLong(),
                        magnit_celevaya.toLong(),
                        perekrestok.toLong(),
                        weight,
                        img_url,
                        1
                    )
                )

                prices[0] += diksi.toLong() * db.getValuesFromCart(currentCart, img_url).toLong()
                if (diksi.toLong() != 0L){ counts[0] += 1L }

                prices[1] += paterocka.toLong() * db.getValuesFromCart(currentCart, img_url).toLong()
                if (paterocka.toLong() != 0L){ counts[1] += 1L }

                prices[2] += magnit_celevaya.toLong() * db.getValuesFromCart(currentCart, img_url).toLong()
                if (magnit_celevaya.toLong() != 0L){ counts[2] += 1L }

                prices[3] += perekrestok.toLong() * db.getValuesFromCart(currentCart, img_url).toLong()
                if (perekrestok.toLong() != 0L){ counts[3] += 1L }

            }

            recyclerViewProducts = findViewById(R.id.product_in_cart_list)
            adapterProducts = ProductsInCartAdapter(productsInCart, this, name_text)
            recyclerViewProducts.adapter = adapterProducts
            recyclerViewProducts.layoutManager = GridLayoutManager(this, 1)

        }

        for (i in 0..3) {
            shops.add(Shop(logos[i], prices[i], counts[i]))
        }


        recyclerViewShops = findViewById(R.id.shops_in_list)
        adapterShops = ShopsAdapter(shops, this, name_text)
        recyclerViewShops.adapter = adapterShops

        val horizontalLayoutManagaer = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewShops.setLayoutManager(horizontalLayoutManagaer)


    }




}





