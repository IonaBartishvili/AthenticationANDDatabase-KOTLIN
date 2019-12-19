package com.example.btuclasroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("UsersInfo") // es me dagimate


        registration_btn.setOnClickListener {
            val first_name = userName.text.toString()
            val last_name = user_lastName.text.toString()
            val email: String = email.text.toString()
            val password: String = password.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

                Toast.makeText(this, "გთხოვთ, შეავსოთ ყველა ველი", Toast.LENGTH_LONG).show()

            } else {

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            writeNewUser(
                                auth.currentUser!!.uid,
                                first_name,
                                last_name,
                                email,
                                password
                            )
                            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG)
                                .show()
                            val goToSignInPage = Intent(this, LoginActivity::class.java)
                            startActivity(goToSignInPage)
                            finish()

                        } else {

                            Toast.makeText(this, "რაღაც გეშლება ბრატცი", Toast.LENGTH_LONG).show()

                        }
                    })

            }

        }

        goBackToLogIn.setOnClickListener {
            val goBackToLogIn = Intent(this, LoginActivity::class.java)
            startActivity(goBackToLogIn)
            finish()
        }

    }

    private fun writeNewUser(
        userID: String,
        name: String,
        surname: String,
        email: String,
        password: String
    ) {
        val userInfo = UserData(name, surname, email, password, userID) // Creating per user Object
        //database.child("Users Info").child(userID).child(email).setValue(userInfo)    // Saving Object to Cloud
        database.child(auth.currentUser?.uid!!).setValue(userInfo)    // Saving Object to Cloud

    }


}