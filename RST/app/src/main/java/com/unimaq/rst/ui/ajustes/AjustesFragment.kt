package com.unimaq.rst.ui.ajustes

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.unimaq.rst.LoginActivity
import com.unimaq.rst.MainActivity
import com.unimaq.rst.R

class AjustesFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_ajustes, container, false)

        val buttonLogout = view.findViewById<Button>(R.id.button_logout)
        buttonLogout?.setOnClickListener {
            val intent = Intent(this.context, LoginActivity::class.java)
            startActivity(intent)
        }

        val userName = activity?.intent?.getStringExtra("username")
        val fullName = activity?.intent?.getStringExtra("fullName")

        view.findViewById<TextView>(R.id.usuario).text = userName
        view.findViewById<TextView>(R.id.nombre).text = fullName

        return view
    }
}