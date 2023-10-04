package com.sharad.sociox.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat.getCategory
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sharad.sociox.Adapter.CategoryProdAdapter
import com.sharad.sociox.Model.AddProductModel
import com.sharad.sociox.R

class CategoryActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        getProducts(intent.getStringExtra(""))
    }

    private fun getProducts(category: String?) {
        val list=ArrayList<AddProductModel>()
        Firebase.firestore.collection("products").whereEqualTo("productCategory",category)
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data =doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                val categoryRecycler=findViewById<RecyclerView>(R.id.categoryRecycler)
                categoryRecycler.adapter= CategoryProdAdapter(this,list)
            }


    }
}