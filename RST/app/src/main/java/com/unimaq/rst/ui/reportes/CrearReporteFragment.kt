package com.unimaq.rst.ui.reportes

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.unimaq.rst.R
import com.unimaq.rst.entities.Atencion
import com.unimaq.rst.entities.Maquinaria
import com.unimaq.rst.proxies.AtencionProxy
import com.unimaq.rst.proxies.MaquinariaProxy
import java.util.concurrent.Executors


class CrearReporteFragment : Fragment() {
    private lateinit var revHidraulicaImage: ImageView
    private lateinit var revAceiteImage: ImageView
    private lateinit var revCalibracionImage: ImageView
    private lateinit var revNeumaticosImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_crear_reporte, container, false)
        setCrearAtencionOnClickListener(view)
        setSpinnerOnItemSelectedListener(view)

        revHidraulicaImage = view.findViewById(R.id.img_rev_hidraulica)
        setImageOnClickLister(view, revHidraulicaImage, changeRevHidraulicaImage)
        revAceiteImage = view.findViewById(R.id.img_rev_aceite)
        setImageOnClickLister(view, revAceiteImage, changeRevAceiteImage)
        revCalibracionImage = view.findViewById(R.id.img_rev_calibracion)
        setImageOnClickLister(view, revCalibracionImage, changeRevCalibracionImage)
        revNeumaticosImage = view.findViewById(R.id.img_rev_neumatico)
        setImageOnClickLister(view, revNeumaticosImage, changeRevNeumaticosImage)

        cargarMaquinarias()

        val data = this.arguments

        if (data != null) {
            val id = data.getLong("id", 0)

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
        view?.findViewById<TextView>(R.id.txt_ultimo_mantenimiento)?.text =
            atencion.ultimo_mantenimiento
        view?.findViewById<TextView>(R.id.txt_horometro)?.text = atencion.horometro
        view?.findViewById<TextView>(R.id.txt_stir_2)?.text = atencion.stir_2
        view?.findViewById<CheckBox>(R.id.chk_revision_hidraulica)?.isChecked =
            atencion.revision_hidraulica
        view?.findViewById<CheckBox>(R.id.chk_revision_aceite)?.isChecked = atencion.revision_aceite
        view?.findViewById<CheckBox>(R.id.chk_revision_calibracion)?.isChecked =
            atencion.revision_calibracion
        view?.findViewById<CheckBox>(R.id.chk_revision_neumaticos)?.isChecked =
            atencion.revision_neumaticos
        view?.findViewById<TextView>(R.id.txt_observaciones)?.text = atencion.observaciones

        loadImage(R.id.img_maquinaria, atencion.maquinaria_imagen)
    }

    private fun loadImage(control: Int, url: String) {
        // Declaring and initializing the ImageView
        val imageView = view?.findViewById<ImageView>(control)

        // Declaring executor to parse the URL
        val executor = Executors.newSingleThreadExecutor()

        // Once the executor parses the URL
        // and receives the image, handler will load it
        // in the ImageView
        val handler = Handler(Looper.getMainLooper())

        // Initializing the image
        var image: Bitmap? = null

        // Only for Background process (can take time depending on the Internet speed)
        executor.execute {

            // Image URL
            val imageURL = url

            // Tries to get the image and post it in the ImageView
            // with the help of Handler
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)

                // Only for making changes in UI
                handler.post {
                    imageView?.setImageBitmap(image)
                }

                imageView?.visibility = View.VISIBLE
            }

            // If the URL doesnot point to
            // image or any other kind of failure
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setSpinnerOnItemSelectedListener(view: View) {
        val spinner = view.findViewById<Spinner>(R.id.maquinaria)
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val maquinaria = parent?.selectedItem as Maquinaria
                loadImage(R.id.img_maquinaria, maquinaria.imagen)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun cargarMaquinarias() {
        this.context?.let { MaquinariaProxy(it).listar("") { data -> cargarMaquinariasCallback(data) } }
    }

    private fun cargarMaquinariasCallback(maquinarias: MutableList<Maquinaria>) {
        val spinner = view?.findViewById<Spinner>(R.id.maquinaria)
        val adapter = ArrayAdapter(
            requireActivity().applicationContext,
            android.R.layout.simple_spinner_item,
            maquinarias
        )

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner.
        spinner?.adapter = adapter

    }

//    private fun setImgRevisionHidraulicaOnClickLister(view: View) {
//        selectedImage = view.findViewById<ImageView>(R.id.img_rev_hidraulica)
//        selectedImage.setOnClickListener {
//            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//            changeImage.launch((pickImg))
//        }
//    }

    private fun setImageOnClickLister(
        view: View,
        control: ImageView,
        launcher: ActivityResultLauncher<Intent>
    ) {
        //val selectedImage = view.findViewById<ImageView>(control)
        control.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            launcher.launch((pickImg))
        }
    }

    private val changeRevHidraulicaImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                revHidraulicaImage.setImageURI(imgUri)
            }
        }

    private val changeRevAceiteImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                revAceiteImage.setImageURI(imgUri)
            }
        }

    private val changeRevCalibracionImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                revCalibracionImage.setImageURI(imgUri)
            }
        }

    private val changeRevNeumaticosImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                revNeumaticosImage.setImageURI(imgUri)
            }
        }
}