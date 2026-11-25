package com.example.sravivatel

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sravivatel.Adapters.CartsAdapter
import com.example.sravivatel.Classes.DBHelper
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdSize
import com.yandex.mobile.ads.common.MobileAds
import com.yandex.mobile.ads.instream.MobileInstreamAds

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var adapter: CartsAdapter
private lateinit var recyclerView: RecyclerView
private lateinit var carts: ArrayList<String>



class CartsFragment : Fragment() {


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carts, container, false)
    }


    companion object {


        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val banner : BannerAdView = view.findViewById(R.id.banner)
        val userInput: EditText = view.findViewById(R.id.cart_name_input)
        val button: Button = view.findViewById(R.id.button_add_cart)
        val db = DBHelper(view.context, null)
        val adRequest = AdRequest.Builder().build()

        banner.setAdUnitId("demo-banner-yandex")
        banner.setAdSize(BannerAdSize.stickySize(view.context, 400))
        banner.loadAd(adRequest)

        carts = db.getTableNames()
        recyclerView = view.findViewById(R.id.cartsList)
        adapter = CartsAdapter(carts, view.context)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 1)

        button.setOnClickListener {
            var name = userInput.text.toString().trim()
            db.addCart(name)

            carts = db.getTableNames()

            val db = DBHelper(view.context, null)
            carts = db.getTableNames()
            recyclerView = view.findViewById(R.id.cartsList)
            adapter = CartsAdapter(carts, view.context)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(context, 1)


        }

    }
}