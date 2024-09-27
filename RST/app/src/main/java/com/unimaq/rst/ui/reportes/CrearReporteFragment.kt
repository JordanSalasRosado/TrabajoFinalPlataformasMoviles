package com.unimaq.rst.ui.reportes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.unimaq.rst.R

class CrearReporteFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_crear_reporte, container, false)
        setCrearInformeOnClickListener(view)

        return view
    }

    private fun setCrearInformeOnClickListener(view: View){
        val button = view.findViewById<Button>(R.id.button_registrar_atencion)
        button?.setOnClickListener{
            loadFragment(CrearReporteFragment())
        }
    }
}