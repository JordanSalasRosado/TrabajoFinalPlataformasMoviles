package com.unimaq.rst.ui.reportes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unimaq.rst.R
import com.unimaq.rst.entities.Atencion
import com.unimaq.rst.proxies.AtencionProxy
import org.w3c.dom.Text

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
        return inflater.inflate(R.layout.fragment_reportes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        adapter = ReportesAdapter(reportes)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
        recyclerView.setAdapter(adapter)

        cargarReportes()
    }

    private fun cargarReportes(){
        this.context?.let { AtencionProxy(it).listar("", { data -> cargarReportesCallback(data)}) }
    }

    fun cargarReportesCallback(atenciones:MutableList<Atencion>){
        val total = view?.findViewById<TextView>(R.id.total)
        total?.text = atenciones.size.toString()
        reportes.addAll(atenciones)
        adapter.notifyDataSetChanged()
    }
}