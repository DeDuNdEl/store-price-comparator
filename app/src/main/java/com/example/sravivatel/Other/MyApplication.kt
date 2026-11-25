package com.example.sravivatel.Other

import android.app.Application
import kotlin.properties.Delegates

lateinit var current_cart: String
lateinit var current_cart_Tech: String
var current_category by Delegates.notNull<Long>()
lateinit var chose_filters: Array<Long>

class MyApplication : Application() {



    fun getCurrentCart(): String {
        return current_cart
    }

    fun getCurrentCart_Tech(): String {
        return current_cart_Tech
    }

    fun getCurrentCategory(): Long {
        return current_category
    }

    fun getChoseFilters(): Array<Long> {
        return chose_filters
    }

    //======================================
    fun setCurrentCart(cart: String) {
        current_cart = cart
    }

    fun setCurrentCart_Tech(cart: String) {
        current_cart_Tech = cart
    }

    fun setCurrentCategory(category: Long) {
        current_category = category

    }

    fun setChoseFilters(data: Array<Long>) {
        chose_filters = data
    }


}