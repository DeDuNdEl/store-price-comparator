package com.example.sravivatel
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sravivatel.Classes.DBHelper
import com.example.sravivatel.Other.MyApplication
import com.example.sravivatel.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yandex.mobile.ads.common.MobileAds
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?): Unit = runBlocking {
        super.onCreate(savedInstanceState)
        val db = DBHelper(baseContext, null)
        var date_text = ""
        var productDonwload = 0
        setContentView(R.layout.loading_menu)

        MobileAds.initialize(baseContext, ) {

        }

        MyApplication().setCurrentCart("корзина не выбрана")
        MyApplication().setChoseFilters(Array<Long>(4){0})

        db.createPropertiesTable()


        if (SimpleDateFormat("dd").format(Date()) != db.getPropertiesTable()){
            database = FirebaseDatabase.getInstance().getReference("test/")
            database.child("count_products").get().addOnSuccessListener {
                productCount = it.getValue() as Long
            }
            database.child("last_update").get().addOnSuccessListener {
                date_text = it.getValue() as String
                db.updatePropertiesTableLastUpdateDB(date_text)
            }


            db.clearLocal_products_DB()
            database = FirebaseDatabase.getInstance().getReference("test/products")
            database.child("count").get().addOnSuccessListener {



                for (i in 0..productCount) {
                    database.child(i.toString()).get().addOnSuccessListener {

                        val name = it.child("0").value.toString()
                        val diksi = it.child("1").value
                        val paterocka = it.child("2").value
                        val magnit_celevaya = it.child("3").value
                        val perekrestok = it.child("4").value
                        val weight = it.child("5").value.toString()
                        val img_url = it.child("6").value.toString()
                        val category = it.child("7").value

                        db.insertToLocal_products_DB(name, diksi.toString().toLong(), paterocka.toString().toLong(), magnit_celevaya.toString().toLong(), perekrestok.toString().toLong(), weight, img_url, category.toString().toLong())

                        productDonwload += 1

                        if (productDonwload.toLong() == productCount){

                            replaceFragment(SearchFragment())

                            db.updatePropertiesTable()

                            binding = ActivityMainBinding.inflate(layoutInflater)
                            setContentView(binding.root)


                            binding.bottomNavigationView.setOnItemSelectedListener {

                                when(it.itemId){

                                    R.id.search -> replaceFragment(SearchFragment())
                                    R.id.carts -> replaceFragment(CartsFragment())

                                }
                                true
                            }
                        }

                    }

                }


                Toast.makeText(baseContext, "база обновилась", Toast.LENGTH_SHORT).show()

            }

        }
        else{

            replaceFragment(SearchFragment())
            Toast.makeText(baseContext, "база уже обновлена", Toast.LENGTH_SHORT).show()

            db.updatePropertiesTable()

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)


            binding.bottomNavigationView.setOnItemSelectedListener {

                when(it.itemId){

                    R.id.search -> replaceFragment(SearchFragment())
                    R.id.carts -> replaceFragment(CartsFragment())

                }
                true
            }
        }
    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}