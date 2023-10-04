package com.sharad.sociox.Activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sharad.sociox.databinding.ActivityAddressBinding

class AddressActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddressBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var totalCost:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.getSharedPreferences("user", MODE_PRIVATE)

        totalCost= intent.getStringExtra("totalCost")!!

        loadUserInfo()
        binding.proceed.setOnClickListener{
            validateData(
                binding.username.text.toString(),
                binding.usernum.text.toString(),
                binding.pincode.text.toString(),
                binding.city.text.toString(),
                binding.village.text.toString(),
                binding.state.text.toString()
            )
        }
    }
    private fun validateData(
        number: String,
        name: String,
        pincode: String,
        city: String,
        village: String,
        state: String
    ) {
        if (number.isEmpty()||state.isEmpty()||name.isEmpty()){
            Toast.makeText(this,"Please Fill All Details", Toast.LENGTH_LONG).show()

        }else{
            storeData(pincode,city,state,village)
        }
    }
    private fun storeData( pincode: String, city: String, state: String, village: String) {
        val map= hashMapOf<String,Any>()
        map["village"]=village
        map["state"]=state
        map["city"]=city
        map["pincode"]=pincode
        Firebase.firestore.collection("users").document(preferences.getString("number","")!!)
            .update(map).addOnSuccessListener {
                val b=Bundle()
                b.putStringArrayList("productIds",intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost",totalCost)
                val intent= Intent(this, CheckoutActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)
            }
            .addOnFailureListener{ Toast.makeText(this,"Something Went Wrong", Toast.LENGTH_LONG).show()}
    }
    private fun loadUserInfo() {
        Firebase.firestore.collection("users").document(preferences.getString("number","")!!)
            .get().addOnSuccessListener {
                binding.username.setText(it.getString("userName"))
                binding.usernum.setText(it.getString("userPhoneNumber"))
                binding.village.setText(it.getString("village"))
                binding.city.setText(it.getString("City"))
                binding.pincode.setText(it.getString("Pincode"))
                binding.state.setText(it.getString("State"))
            }
            .addOnFailureListener{}
    }
}