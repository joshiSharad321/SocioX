package com.sharad.sociox.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.sharad.sociox.MainActivity
import com.sharad.sociox.databinding.ActivityOtpactivityBinding


class OTPActivity : AppCompatActivity() {
private lateinit var binding: ActivityOtpactivityBinding
   private  var forceResendingToken:PhoneAuthProvider.ForceResendingToken?=null
   private var mCallBacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks?=null
    private var mVerificationId:String?=null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)

binding = ActivityOtpactivityBinding.inflate(layoutInflater)
setContentView(binding.root)






binding.button3.setOnClickListener {
    if (binding.userOtp.text!!.isEmpty())
      Toast.makeText(this, "Please Enter O.T.P.", Toast.LENGTH_LONG).show()
else {
     verifyUser(binding.userOtp.text.toString()) }
}
}
    private lateinit var builder:AlertDialog
    private fun resendVerificationCode(number: String,token: PhoneAuthProvider.ForceResendingToken?){
        fun onClick(view: View?) {

        }

}

  private fun verifyUser(otp: String) {
    val credential = PhoneAuthProvider.getCredential(intent.getStringExtra("verificationId")!!, otp)
    signInWithPhoneAuthCredential(credential)
}
   private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
       FirebaseAuth.getInstance().signInWithCredential(credential)
                 .addOnCompleteListener(this) { task ->
   if (task.isSuccessful) {
    val preferences=this.getSharedPreferences("user", MODE_PRIVATE)
    val editor=preferences.edit()
   editor.putString("number",intent.getStringExtra("number")!!)
   editor.apply()

     startActivity(Intent(this,MainActivity::class.java))
       finish()
} else {

     Toast.makeText( this@OTPActivity,"Something Went Wrong",Toast.LENGTH_LONG).show()
    finish()
}
}
}
}
