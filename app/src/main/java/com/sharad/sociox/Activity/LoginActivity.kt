package com.sharad.sociox.Activity

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider

import com.sharad.sociox.R
import com.sharad.sociox.databinding.ActivityLoginBinding
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private var mVerificationId:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button4.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }

            binding.button3.setOnClickListener{
                if (binding.userNum.text!!.isEmpty())
                    Toast.makeText(this,"Please Enter Phone Number", Toast.LENGTH_SHORT).show()
                else
                    sendOtp(binding.userNum.text.toString())
            }


    }
    private lateinit var builder:AlertDialog
    private fun sendOtp(number: String) {
        builder= AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()
        builder.show()
        val options= PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+91$number")
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
      val  callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        }
        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(this@LoginActivity,"O.T.P Verification Failed",Toast.LENGTH_LONG).show()}
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            builder.dismiss()
            val intent=Intent(this@LoginActivity,OTPActivity::class.java)
            intent.putExtra("verificationId",verificationId)
            intent.putExtra("number",binding.userNum.text!!.toString())
            startActivity(intent)
        }
    }
}