package com.sharad.sociox.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sharad.sociox.Adapter.AllOrderAdapter


import com.sharad.sociox.Model.AllOrderModal
import com.sharad.sociox.R
import com.sharad.sociox.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {

   private lateinit var binding: FragmentMoreBinding
    private lateinit var list:ArrayList<AllOrderModal>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMoreBinding.inflate(layoutInflater)
        list= ArrayList()

        val preferences=requireContext().getSharedPreferences("user",AppCompatActivity.MODE_PRIVATE)
        Firebase.firestore.collection("allOrder").whereEqualTo("userId",preferences.getString("number","")!!)
            .get().addOnSuccessListener {
            list.clear()
            for (doc in it){
                val data=doc.toObject(AllOrderModal::class.java)
                list.add(data)
            }
           binding.recyclerView.adapter= AllOrderAdapter(list,requireContext())
        }
          return binding.root


    }




}