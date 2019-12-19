package com.example.btuclasroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        // ავტორიზაციის ლოგიკა
        log_in_btn.setOnClickListener {
            val email: String = email.text.toString()
            val password: String = password.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "მოხმარებელი ან პაროლი არასწორია !", Toast.LENGTH_LONG).show()

            } else {

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, OnCompleteListener { task ->

                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "ავტორიზაცია წარმატებით განხორციელდა",
                                Toast.LENGTH_LONG
                            ).show()
                            val signInToAcc = Intent(this, MainActivity::class.java)
                            startActivity(signInToAcc)
                            finish()
                        } else {
                            Toast.makeText(this, "შეცდომა!", Toast.LENGTH_LONG).show()
                        }

                    })
            }
        }


        // გადავიდეს რეგისტრაციის გვერდზე
        registration_btn.setOnClickListener {
            val goToRegistrationPage = Intent(this, RegisterActivity::class.java)
            startActivity(goToRegistrationPage)
            finish()
        }

        forgot_password.setOnClickListener {
            // გადავიდეს დამავიწყდა პაროლის გვერდზე
        }


    }
}
