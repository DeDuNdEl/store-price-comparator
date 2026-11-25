package com.example.sravivatel

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sravivatel.Adapters.CategoriesAdapter
import com.example.sravivatel.Adapters.ProductsAdapter
import com.example.sravivatel.Classes.Category
import com.example.sravivatel.Classes.DBHelper
import com.example.sravivatel.Classes.Product
import com.example.sravivatel.Other.MyApplication
import com.example.sravivatel.Other.current_category
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.database.DatabaseReference

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


private lateinit var adapter_product: ProductsAdapter
private lateinit var recyclerView_product: RecyclerView

private lateinit var adapter_category: CategoriesAdapter
private lateinit var recyclerView_category: RecyclerView

private lateinit var products: kotlin.collections.ArrayList<Product>
private lateinit var database: DatabaseReference

var productCount = 0L


class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }




    fun clickOnCategory(view: View, category_ID: Long){
        val db = DBHelper(view.context, null)
        MyApplication().setCurrentCategory(category_ID)

        recyclerView_category = view.findViewById(R.id.categoriesList)

        recyclerView_category.isVisible = false

        for (i in db.getTableData("local_products_DB")){
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

        }

        var userProducts = arrayListOf<Product>()
        val array_filter = MyApplication().getChoseFilters()
        for (i in products) {
            if ((category_ID == i.category) and (i.cost_diksi >= array_filter[0]) and (i.cost_paterocka >= array_filter[1]) and (i.cost_magnit_celevaya >= array_filter[2]) and (i.cost_perekrestok >= array_filter[3]) ) {
                userProducts.add(i)
            }
        }

        if (userProducts.size == 0) {
            Toast.makeText(context, "Ничего не найдено :(", Toast.LENGTH_SHORT).show()
        }

        recyclerView_product = view.findViewById(R.id.productsList)

        recyclerView_product.isVisible = true

        adapter_product = ProductsAdapter(userProducts, view.context)
        recyclerView_product.adapter = adapter_product
        recyclerView_product.layoutManager = GridLayoutManager(context, 2)

    }
    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        dataInitialize()
        val userInput: EditText = view.findViewById(R.id.input_search)
        val button_search: Button = view.findViewById(R.id.button_search)
        val button_filter: Button = view.findViewById(R.id.button_filter)


        var date_of_update_DB: TextView = view.findViewById(R.id.date_update_DB)
        val db = DBHelper(view.context, null)
        val categoriesID =  arrayListOf<Category>(Category(165, "https://eda.yandex/images/3735503/089ef61d2ad31a17baf0aec4aa2d8b8d.png/200x200"), Category(19280, "https://eda.yandex/images/3513074/c4026e3b49b8acc8802359ab634f97cb.png/200x200"), Category(1034,"https://eda.yandex/images/3337779/d10c74a161cd75522451f98aed228103.png/200x200"), Category(1033, "https://eda.yandex/images/3512182/d66c7bf97375d545a48eaa7b0c1390d2.png/200x200"), Category(139, "https://eda.yandex/images/3528285/efe3e832a1e32dec0ddf13ef5be7a458.png/200x200"), Category(178, "https://eda.yandex/images/3806466/99d8d96912b57876bbae4791321df8a6.png/200x200"), Category(147, "https://eda.yandex/images/3439767/230e2cceaf819945c6f89521644b13e0.png/200x200"), Category(12881, "https://eda.yandex/images/2050703/29c51f0b1cf52fd0b8b62a2ffc1f0302.png/200x200"), Category(148, "https://eda.yandex/images/3504598/900cae434fff7397761839e1f96f7cd4.png/200x200"), Category(182, "https://eda.yandex/images/3521394/c859fbc5fa52bae3213f38e38759c14b.png/200x200"), Category(167, "https://eda.yandex/images/2394388/e9f59c84b34ef26e2172e7e88af29d8c.png/200x200"), Category(19288, "https://eda.yandex/images/3439767/3ca6e01e01e366b14660f8e174ae08be.png/200x200"), Category(38776, "https://eda.yandex/images/3559865/50b8ab1153a44c7eba7d4ee547dcb80b.png/200x200"), Category(19283, "https://eda.yandex/images/3504598/05deb765fc602f31c46415a67a2edd57.png/200x200"), Category(19291, "https://eda.yandex/images/2783965/ee168f1a50f5f8c7726c03310fb6cc8b.png/200x200"), Category(30191, "https://eda.yandex/images/3583740/5cd8ea3aae2c4b51cc4c48185b07df0e.png/200x200"), Category(21575, "https://eda.yandex/images/3531870/5977538411fb466a034140de4bc2052f.png/200x200"), Category(3374, "https://eda.yandex/images/3208959/4a278385ed0dd3ec216deab6e590b9f3.png/200x200"), Category(19287, "https://eda.yandex/images/3528150/2ff2ffb7b208eecff3d59fd3a7c1e3d3.png/200x200"), Category(19286, "https://eda.yandex/images/2394388/870d15d72f4395a25809b88cf6533c9f.png/200x200"), Category(44559, "https://eda.yandex/images/3490902/abca067a36efbf9f49ac3a5883d524b7.png/200x200"))



        recyclerView_category = view.findViewById(R.id.categoriesList)
        adapter_category = CategoriesAdapter(categoriesID, this@SearchFragment, view)
        recyclerView_category.adapter = adapter_category
        recyclerView_category.layoutManager = GridLayoutManager(context, 2)

        date_of_update_DB.text = db.getLastUpdateDB()
        MyApplication().setCurrentCategory(0L)


        button_search.setOnClickListener {

            recyclerView_category.isVisible = false
            for (i in db.getTableData("local_products_DB")){
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

            }

            var text = userInput.text.toString().trim()



            var userProducts = arrayListOf<Product>()
            current_category = MyApplication().getCurrentCategory()
            val array_filter = MyApplication().getChoseFilters()

            if (current_category == 0L){
                for (i in products) {
                    if  ((text in i.name) and (i.cost_diksi >= array_filter[0]) and (i.cost_paterocka >= array_filter[1]) and (i.cost_magnit_celevaya >= array_filter[2]) and (i.cost_perekrestok >= array_filter[3])) {
                        userProducts.add(i)
                    }
                }
            }
            else{
                for (i in products) {
                    if ( (text in i.name)  and (i.category == current_category) and (i.cost_diksi >= array_filter[0]) and (i.cost_paterocka >= array_filter[1]) and (i.cost_magnit_celevaya >= array_filter[2]) and (i.cost_perekrestok >= array_filter[3])) {
                        userProducts.add(i)
                    }
                }
            }


            if (userProducts.size == 0) {
                Toast.makeText(context, "Ничего не найдено :(", Toast.LENGTH_SHORT).show()
            }


            recyclerView_product = view.findViewById(R.id.productsList)
            adapter_product = ProductsAdapter(userProducts, view.context)
            recyclerView_product.adapter = adapter_product
            recyclerView_product.layoutManager = GridLayoutManager(context, 2)


        }

        button_filter.setOnClickListener{
            val intent = Intent(context, FilterActivity::class.java)
            view.context.startActivity(intent)
        }


    }

    private fun dataInitialize(){

        products = arrayListOf<Product>()


    }
}