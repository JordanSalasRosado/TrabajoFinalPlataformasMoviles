package com.unimaq.rst

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isLogged = intent.getBooleanExtra("isLogged", false)

        if (isLogged) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("username", intent.getStringExtra("username"))
            intent.putExtra("fullName", intent.getStringExtra("fullName"))
            startActivity(intent)
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}