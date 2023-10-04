package com.sharad.sociox.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sharad.sociox.Model.UserModel
import com.sharad.sociox.R
import com.sharad.sociox.databinding.ActivityRegisterBinding
import java.lang.reflect.Array.set

class RegisterActivity : AppCompatActivity() {
   private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button4.setOnClickListener{
            openLogin()
        }
        binding.button3.setOnClickListener{
            validateUser()

        }
    }

    private fun validateUser() {
        if (binding.userName.text!!.isEmpty()|| binding.userNum.text!!.isEmpty())
            Toast.makeText(this,"Fields cannot be left empty",Toast.LENGTH_SHORT).show()
        else storeData()

    }
    private fun storeData() {
       val builder=AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()
    builder.show()

        val preferences=this.getSharedPreferences("user", MODE_PRIVATE)
        val editor=preferences.edit()
        editor.putString("number",binding.userNum.text.toString())
        editor.putString("name",binding.userName.text.toString())
        editor.apply()



        val data=UserModel(userName= binding.userName.text!!.toString(),userPhoneNumber=binding.userNum.text!!.toString())
        Firebase.firestore.collection("users").document(binding.userNum.text!!.toString())
            .set(data).addOnSuccessListener {
                Toast.makeText(this,"Successfully Registered",Toast.LENGTH_SHORT).show()
               builder.dismiss()
                openLogin()

            }
            .addOnFailureListener{
                builder.dismiss()
               Toast.makeText(this,"Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
    }
    private fun openLogin() {
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

}