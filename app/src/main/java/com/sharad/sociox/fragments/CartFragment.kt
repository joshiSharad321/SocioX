package com.sharad.sociox.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import com.sharad.sociox.Activity.AddressActivity
import com.sharad.sociox.Adapter.CartAdapter
import com.sharad.sociox.Model.ProductModel
import com.sharad.sociox.R
import com.sharad.sociox.databinding.FragmentCartBinding
import com.sharad.sociox.roomDB.AppDatabase

class CartFragment : Fragment() {

    private lateinit var list: ArrayList<String>

    private lateinit var binding: FragmentCartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)

//If user paused the app on cartFragment then the app won't restart on cartFragment
        val preference = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart", false)
        editor.apply()

        val dao = AppDatabase.getInstance(requireContext()).productDao()
        list = ArrayList()
        dao.getAllProduct().observe(requireActivity()) {
            binding.cartRecycler.adapter = CartAdapter(requireContext(), it)
            list.clear()
            for (data in it) {
                list.add(data.productId)
            }

            totalCost(it)
        }
        return binding.root
    }

    private fun totalCost(data: List<ProductModel>?) {
        var total = 0
        for (item in data!!) {
            total += item.productSp!!.toInt()
        }
        binding.textView12.text = "Total item in cart are ${data.size}"
        binding.textView13.text = "Total Cost:$total"


        binding.checkout.setOnClickListener {
            val intent = Intent(context, AddressActivity::class.java)
            val b = Bundle()
            b.putStringArrayList("productIds", list)
            b.putString("totalCost", total.toString())
            intent.putExtras(b)
            startActivity(intent)
        }
    }


}