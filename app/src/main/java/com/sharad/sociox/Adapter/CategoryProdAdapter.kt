package com.sharad.sociox.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.sharad.sociox.Activity.ProductDetailsActivity
import com.sharad.sociox.Model.AddProductModel
import com.sharad.sociox.databinding.CategoryProductLayoutBinding

class CategoryProdAdapter (val context: Context,val list:ArrayList<AddProductModel>)
        :RecyclerView.Adapter<CategoryProdAdapter.CategoryProdViewHolder>(){

inner class CategoryProdViewHolder(val binding:CategoryProductLayoutBinding)
    :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProdViewHolder {
        val binding=CategoryProductLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryProdViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryProdViewHolder, position: Int) {
        Glide.with(context).load(list[position].productCoverImg).into(holder.binding.imageView3)
        holder.binding.textView5.text=list[position].productName
        holder.binding.textView6.text=list[position].productSp
        holder.itemView.setOnClickListener{
            val intent=Intent(context,ProductDetailsActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}