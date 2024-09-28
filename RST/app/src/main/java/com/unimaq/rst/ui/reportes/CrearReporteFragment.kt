package com.unimaq.rst.ui.reportes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.unimaq.rst.R
import com.unimaq.rst.entities.Atencion
import com.unimaq.rst.proxies.AtencionProxy

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
        setCrearAtencionOnClickListener(view)

        val data = this.arguments

        if (data != null) {
            var id = data.getLong("id", 0)

            if (id > 0) {
                cargarAtencion(id)
            }
        }

        return view
    }

    private fun setCrearAtencionOnClickListener(view: View) {
        val button = view.findViewById<Button>(R.id.button_registrar_atencion)
        button?.setOnClickListener {

            val numero_solicitud = view.findViewById<EditText>(R.id.txt_numero_solicitud)
            val id_maquinaria = 1.toLong()
            val numero_serie = view.findViewById<EditText>(R.id.txt_numero_serie)
            val anho_fabricacion = view.findViewById<EditText>(R.id.txt_anho_fabricacion)
            val ultimo_mantenimiento = view.findViewById<EditText>(R.id.txt_ultimo_mantenimiento)
            val horometro = view.findViewById<EditText>(R.id.txt_horometro)
            val stir_2 = view.findViewById<EditText>(R.id.txt_str_2)
            val revision_hidraulica = view.findViewById<CheckBox>(R.id.chk_revision_hidraulica)
            val revision_aceite = view.findViewById<CheckBox>(R.id.chk_revision_aceite)
            val revision_calibracion = view.findViewById<CheckBox>(R.id.chk_revision_calibracion)
            val revision_neumaticos = view.findViewById<CheckBox>(R.id.chk_revision_neumaticos)
            val observaciones = view.findViewById<EditText>(R.id.txt_observaciones)
            val firma_tecnico = ""
            val firma_supervisor = ""
            val fecha_creacion = ""
            val fecha_modificacion = ""
            val maquinaria_marca = ""
            val maquinaria_categoria = ""
            val maquinaria_modelo = ""

            var atencion = Atencion(
                0,
                numero_solicitud.text.toString(),
                id_maquinaria,
                numero_serie.text.toString(),
                anho_fabricacion.text.toString(),
                ultimo_mantenimiento.text.toString(),
                horometro.text.toString(),
                stir_2.text.toString(),
                revision_hidraulica.isChecked,
                revision_aceite.isChecked,
                revision_calibracion.isChecked,
                revision_neumaticos.isChecked,
                observaciones.text.toString(),
                firma_tecnico,
                firma_supervisor,
                fecha_creacion,
                fecha_modificacion,
                maquinaria_marca,
                maquinaria_categoria,
                maquinaria_modelo
            )

            this.context?.let { AtencionProxy(it).guardar(atencion) }
        }
    }

    private fun cargarAtencion(id: Long) {
        this.context?.let { AtencionProxy(it).obtener(id) { data -> cargarAtencionCallback(data) } }
    }

    fun cargarAtencionCallback(atencion: Atencion) {
        view?.findViewById<TextView>(R.id.txt_numero_solicitud)?.text = atencion.numero_solicitud
        view?.findViewById<TextView>(R.id.txt_numero_serie)?.text = atencion.numero_serie
        view?.findViewById<TextView>(R.id.txt_anho_fabricacion)?.text = atencion.anho_fabricacion
    }
}