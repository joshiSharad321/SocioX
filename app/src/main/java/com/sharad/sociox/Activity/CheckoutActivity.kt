package com.sharad.sociox.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener



import com.sharad.sociox.MainActivity
import com.sharad.sociox.Model.ProductModel

import com.sharad.sociox.R
import com.sharad.sociox.roomDB.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class CheckoutActivity : AppCompatActivity(), PaymentResultListener {


    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_checkout)
    val checkout= Checkout()
    checkout.setKeyID("<Your_Key_ID>")

    val price=intent.getStringExtra("totalCost")
    try {
    val options=JSONObject()
    options.put("name", "Social Shops");
    options.put("description", "Your Local Shop");
    options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
    options.put("theme.color", "#3399cc");
    options.put("currency", "INR");
    options.put("amount", (price!!.toInt()*100).toString())//pass amount in currency subunits//
    options.put("prefill.email", "joshikaran597@gmail.com")
    options.put("prefill.contact","9509838928");
    checkout.open(this, options)
    } catch(e:Exception ) {
    Toast.makeText(this,"Something Went Wrong,Check Internet Connections",Toast.LENGTH_LONG).show()
    }
    }
    override fun onPaymentSuccess(p0: String?) {
    Toast.makeText(this,"Transaction Successfully Done",Toast.LENGTH_LONG).show()
    uploadData()
    }

    private fun uploadData() {
    val id = intent.getStringArrayListExtra("productIds")
    for (currentId in id!!) {
    fetchData(currentId)
    }
    }
    private fun fetchData(productId: String?) {

    val dao= AppDatabase.getInstance(this).productDao()
    Firebase.firestore.collection("")
    .document(productId!!).get().addOnSuccessListener {
    lifecycleScope.launch(Dispatchers.IO) {
    dao.deleteProduct(ProductModel(productId))
    }


    saveData(it.getString("product Name"),
    it.getString("productSp"),productId
    ) }

    }

    override fun onPaymentError(p0: Int, p1: String?) {
    Toast.makeText(this,"Transaction Failure",Toast.LENGTH_LONG).show()
    }
    private fun saveData(name: String?,price: String?,productId: String){
    val preferences=this.getSharedPreferences("user", MODE_PRIVATE)
    val data= hashMapOf<String,Any>()
    data["name"]=name!!
    data["price"]=price!!
    data["productId"]=productId
    data["status"]="Ordered"
    data["userId"]=preferences.getString("number","")!!

    val firestore=Firebase.firestore.collection("allOrders")
    val key=firestore.document().id
    data["orderId"]=key

    firestore.document(key).set(data).addOnSuccessListener {
    Toast.makeText(this,"Order Placed",Toast.LENGTH_LONG).show()
    startActivity(Intent(this,MainActivity::class.java))
    finish()

    }.addOnFailureListener{
    Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_LONG).show()
    }

}    }



