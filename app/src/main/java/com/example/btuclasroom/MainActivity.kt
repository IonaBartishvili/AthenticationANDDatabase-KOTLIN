package com.example.btuclasroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("UsersInfo")

        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        setContentView(R.layout.activity_main)

        userEmail.text = auth.currentUser?.email
        userID.text = auth.currentUser?.uid

        FirebaseDatabase.getInstance().reference.child("UsersInfo").child(auth.currentUser!!.uid).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, "Something Went Wrong!", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val fullName = p0.child("name").value.toString() + " " + p0.child("surname").value.toString()

                user_name.setText(fullName).toString()

            }
        })


        log_out_btn.setOnClickListener {
            auth.signOut()
            val goToLogInPage = Intent(this, LoginActivity::class.java)
            startActivity(goToLogInPage)
            finish()
        }

    }


}
