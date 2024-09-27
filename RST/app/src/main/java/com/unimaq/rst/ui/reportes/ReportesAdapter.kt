package com.unimaq.rst.ui.reportes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unimaq.rst.R
import com.unimaq.rst.entities.Atencion
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReportesAdapter(private val reportes: List<Atencion>) :
    RecyclerView.Adapter<ReportesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = ViewHolder(layoutInflater.inflate(R.layout.reportes_item, parent, false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val obj = reportes[position]
        holder.numero_solicitud.text = obj.numero_solicitud
        holder.maquinaria.text = obj.maquinaria()
        holder.ultimo_mantenimiento.text= obj.ultimo_mantenimiento





        //val date = LocalDate.parse("2022-01-06",DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))

//        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-ddThh:mm:ss")
//        val dateFormat2 = DateTimeFormatter.ofPattern("yyyy/MM/dd")
//        var date =LocalDateTime.parse(obj.ultimo_mantenimiento, dateFormat)
//
//        holder.ultimo_mantenimiento.text = date.format(dateFormat2)
            //SimpleDateFormat("dd/MM/yyyy").format(obj.ultimo_mantenimiento)
            //obj.ultimo_mantenimiento

        //val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        //val date = LocalDate.parse(obj.ultimo_mantenimiento , dateFormat)

    }

    override fun getItemCount(): Int {
        return reportes.size
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val numero_solicitud = view.findViewById<TextView>(R.id.numero_solicitud)
        val maquinaria = view.findViewById<TextView>(R.id.maquinaria)
        val ultimo_mantenimiento = view.findViewById<TextView>(R.id.ultimo_mantenimiento)
    }
}