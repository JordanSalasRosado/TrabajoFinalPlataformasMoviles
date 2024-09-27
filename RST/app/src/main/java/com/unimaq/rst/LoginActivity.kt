package com.unimaq.rst

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.unimaq.rst.entities.User

class LoginActivity : AppCompatActivity() {
    private val allowedUsers = mapOf(
        "u201021101@upc.edu.pe" to
                User("u201021101@upc.edu.pe","12345","Victor Renzo Cisneros"),
        "U202018455@upc.edu.pe" to
                User("U202018455@upc.edu.pe","12345","Cristhian Huamaní Murrieta"),
        "u201824058@upc.edu.pe" to
                User("u201824058@upc.edu.pe","12345","Angel Paolo Livia Rivas"),
        "" to
                User("","","Javier Huiza")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        setLoginOnClickListener()
    }

    private fun setLoginOnClickListener() {
        val button = findViewById<TextView>(R.id.button_login)
        button.setOnClickListener {
            val username =  findViewById<EditText?>(R.id.username).text.toString()
            val password = findViewById<EditText?>(R.id.password).text.toString()

            val userMatch = allowedUsers.get(username)

            if (userMatch != null){

                if (userMatch.password == password){
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("isLogged", true)
                    intent.putExtra("username", userMatch.username)
                    intent.putExtra("fullName", userMatch.fullName)

                    startActivity(intent)
                }else{
                    Toast.makeText(this,"Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}