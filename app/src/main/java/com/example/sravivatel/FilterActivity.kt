package com.example.sravivatel

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sravivatel.Adapters.ProductsInCartAdapter
import com.example.sravivatel.Adapters.ShopsAdapter
import com.example.sravivatel.Classes.DBHelper
import com.example.sravivatel.Classes.Product
import com.example.sravivatel.Classes.ProductInCart
import com.example.sravivatel.Classes.Shop
import com.example.sravivatel.Other.MyApplication

class FilterActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filter_activity)

        val diksi: CheckBox = findViewById(R.id.checkBox_diksi)
        val paterocka: CheckBox = findViewById(R.id.checkBox_paterocka)
        val magnit_celevaya: CheckBox = findViewById(R.id.checkBox_magnit_celevaya)
        val perekrestok: CheckBox = findViewById(R.id.checkBox_perekrestok)

        val array_filters = MyApplication().getChoseFilters()

        if (array_filters[0] == 1L){
            diksi.isChecked = true
        }
        if (array_filters[1] == 1L){
            paterocka.isChecked = true
        }
        if (array_filters[2] == 1L){
            magnit_celevaya.isChecked = true
        }
        if (array_filters[3] == 1L){
            perekrestok.isChecked = true
        }

        diksi.setOnClickListener{
            var arr = MyApplication().getChoseFilters()
            if (arr[0] == 1L){
                arr[0] = 0L
                diksi.isChecked = false
            }
            else{
                arr[0] = 1L
                diksi.isChecked = true
            }

        }
        paterocka.setOnClickListener{
            var arr = MyApplication().getChoseFilters()
            if (arr[1] == 1L){
                arr[1] = 0L
                paterocka.isChecked = false
            }
            else{
                arr[1] = 1L
                paterocka.isChecked = true
            }
        }
        magnit_celevaya.setOnClickListener{
            var arr = MyApplication().getChoseFilters()
            if (arr[2] == 1L){
                arr[2] = 0L
                magnit_celevaya.isChecked = false
            }
            else{
                arr[2] = 1L
                magnit_celevaya.isChecked = true
            }
        }
        perekrestok.setOnClickListener{
            var arr = MyApplication().getChoseFilters()
            if (arr[3] == 1L){
                arr[3] = 0L
                perekrestok.isChecked = false
            }
            else{
                arr[3] = 1L
                perekrestok.isChecked = true
            }
        }



    }
}