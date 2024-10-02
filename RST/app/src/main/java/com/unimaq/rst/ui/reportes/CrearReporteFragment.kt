package com.unimaq.rst.ui.reportes

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.unimaq.rst.R
import com.unimaq.rst.entities.Atencion
import com.unimaq.rst.proxies.AtencionProxy
import java.net.URL

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
            val stir_2 = view.findViewById<EditText>(R.id.txt_stir_2)
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
            val maquinaria_imagen = ""

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
                maquinaria_modelo,
                maquinaria_imagen
            )

            this.context?.let { AtencionProxy(it).guardar(atencion) }
        }
    }

    private fun cargarAtencion(id: Long) {
        this.context?.let { AtencionProxy(it).obtener(id) { data -> cargarAtencionCallback(data) } }
    }

    private fun cargarAtencionCallback(atencion: Atencion) {
        view?.findViewById<TextView>(R.id.txt_numero_solicitud)?.text = atencion.numero_solicitud
        view?.findViewById<TextView>(R.id.txt_numero_serie)?.text = atencion.numero_serie
        view?.findViewById<TextView>(R.id.txt_anho_fabricacion)?.text = atencion.anho_fabricacion
        view?.findViewById<TextView>(R.id.txt_ultimo_mantenimiento)?.text = atencion.ultimo_mantenimiento
        view?.findViewById<TextView>(R.id.txt_horometro)?.text = atencion.horometro
        view?.findViewById<TextView>(R.id.txt_stir_2)?.text = atencion.stir_2
        view?.findViewById<CheckBox>(R.id.chk_revision_hidraulica)?.isChecked = atencion.revision_hidraulica
        view?.findViewById<CheckBox>(R.id.chk_revision_aceite)?.isChecked = atencion.revision_aceite
        view?.findViewById<CheckBox>(R.id.chk_revision_calibracion)?.isChecked = atencion.revision_calibracion
        view?.findViewById<CheckBox>(R.id.chk_revision_neumaticos)?.isChecked = atencion.revision_neumaticos
        view?.findViewById<TextView>(R.id.txt_observaciones)?.text = atencion.observaciones
        val url = URL(atencion.maquinaria_imagen)
        try {
            val image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            view?.findViewById<ImageView>(R.id.img_maquinaria)?.setImageBitmap(image)
        }
       catch (ex: Exception){
           Log.i("======>", ex.message.toString())
       }
    }
}