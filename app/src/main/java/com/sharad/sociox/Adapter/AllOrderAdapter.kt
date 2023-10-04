package com.sharad.sociox.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.sharad.sociox.Model.AllOrderModal
import com.sharad.sociox.databinding.AllOrderItemLayoutBinding



class AllOrderAdapter (val list:ArrayList<AllOrderModal>, val context: Context)
    :RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder>() {

    inner class AllOrderViewHolder(val binding: AllOrderItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
        return AllOrderViewHolder(
            AllOrderItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {
        holder.binding.prodTitle.text = list[position].name
        holder.binding.prodPrice.text = list[position].price

        when (list[position].status) {
            "Ordered" -> {
                holder.binding.prodStatus.text = "Ordered"
            }

            "Dispatched" -> {
                holder.binding.prodStatus.text = "Dispatched"
            }

            "Delivered" -> {
                holder.binding.prodStatus.text = "Delivered"

            }
            "Canceled" -> {
                holder.binding.prodStatus.text="Delivered"
            }

        }

    }


    override fun getItemCount(): Int {
        return list.size
    }


}