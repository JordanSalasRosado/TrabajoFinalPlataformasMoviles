package com.unimaq.rst.ui.reportes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unimaq.rst.R
import com.unimaq.rst.entities.Atencion
import com.unimaq.rst.entities.Maquinaria
import com.unimaq.rst.proxies.AtencionProxy
import com.unimaq.rst.proxies.MaquinariaProxy

class ReportesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReportesAdapter
    private val reportes: ArrayList<Atencion> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reportes, container, false)
        setRegistrarAtencionOnClickListener(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        adapter = ReportesAdapter(reportes)
        activity?.let { adapter.constructor(it) }
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.setAdapter(adapter)

        cargarAtenciones()
    }

    private fun cargarAtenciones() {
        this.context?.let { AtencionProxy(it).listar("") { data -> cargarAtencionesCallback(data) } }
    }

    fun cargarAtencionesCallback(atenciones: MutableList<Atencion>) {
        val total = view?.findViewById<TextView>(R.id.total)
        total?.text = atenciones.size.toString()
        reportes.addAll(atenciones)
        adapter.notifyDataSetChanged()
    }

    private fun setRegistrarAtencionOnClickListener(view: View) {
        val button = view.findViewById<Button>(R.id.button_registrar_atencion)
        button?.setOnClickListener {
            loadFragment(CrearReporteFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.nav_host_fragment_activity_main, fragment)

        transaction?.commit()
    }
}