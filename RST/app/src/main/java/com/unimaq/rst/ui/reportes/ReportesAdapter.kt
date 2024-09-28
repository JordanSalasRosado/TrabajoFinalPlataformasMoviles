package com.unimaq.rst.ui.reportes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.unimaq.rst.MainActivity
import com.unimaq.rst.R
import com.unimaq.rst.entities.Atencion


class ReportesAdapter(private val reportes: List<Atencion>) :
    RecyclerView.Adapter<ReportesAdapter.ViewHolder>() {

    private lateinit var _context: FragmentActivity

    fun constructor(context: FragmentActivity) {
        _context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = ViewHolder(layoutInflater.inflate(R.layout.reportes_item, parent, false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val obj = reportes[position]

        holder.id.tag = obj.id
        holder.numero_solicitud.text = obj.numero_solicitud
        holder.maquinaria.text = obj.maquinaria()
        holder.ultimo_mantenimiento.text = obj.ultimo_mantenimiento
        holder.button_ver.setOnClickListener {
            val fragment = CrearReporteFragment()
            val data = Bundle()
            data.putLong("id", obj.id)
            fragment.arguments = data
            loadFragment(fragment)
        }
    }

    override fun getItemCount(): Int {
        return reportes.size
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val id = view.findViewById<View>(R.id.id)
        val numero_solicitud = view.findViewById<TextView>(R.id.numero_solicitud)
        val maquinaria = view.findViewById<TextView>(R.id.maquinaria)
        val ultimo_mantenimiento = view.findViewById<TextView>(R.id.ultimo_mantenimiento)
        val button_ver = view.findViewById<Button>(R.id.button_ver)
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = _context.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_activity_main, fragment)

        transaction.commit()
    }
}