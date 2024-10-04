package com.unimaq.rst.ui.reportes

import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.DocumentsContract
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
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.unimaq.rst.R
import com.unimaq.rst.aws.AWSUtils
import com.unimaq.rst.aws.AwsConstants
import com.unimaq.rst.entities.Atencion
import com.unimaq.rst.entities.Maquinaria
import com.unimaq.rst.proxies.AtencionProxy
import com.unimaq.rst.proxies.MaquinariaProxy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.Executors


class CrearReporteFragment : Fragment(), AWSUtils.OnAwsImageUploadListener {
    private lateinit var revHidraulicaImage: ImageView
    private lateinit var revAceiteImage: ImageView
    private lateinit var revCalibracionImage: ImageView
    private lateinit var revNeumaticosImage: ImageView
    private lateinit var firma_tecnicoImage: ImageView
    private lateinit var firma_supervisorImage: ImageView
    private var entityId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_crear_reporte, container, false)
        val bar = (activity as AppCompatActivity?)!!.supportActionBar
        bar?.title = "Registro de Atención"
        bar?.setDisplayHomeAsUpEnabled(true)

        setCrearAtencionOnClickListener(view)
        setSpinnerOnItemSelectedListener(view)

        revHidraulicaImage = view.findViewById(R.id.img_rev_hidraulica)
        setImageOnClickListener(view, revHidraulicaImage, changeRevHidraulicaImage)
        revAceiteImage = view.findViewById(R.id.img_rev_aceite)
        setImageOnClickListener(view, revAceiteImage, changeRevAceiteImage)
        revCalibracionImage = view.findViewById(R.id.img_rev_calibracion)
        setImageOnClickListener(view, revCalibracionImage, changeRevCalibracionImage)
        revNeumaticosImage = view.findViewById(R.id.img_rev_neumatico)
        setImageOnClickListener(view, revNeumaticosImage, changeRevNeumaticosImage)

        firma_tecnicoImage = view.findViewById(R.id.img_firma_tecnico)
        setImageOnClickListener(view, firma_tecnicoImage, changeFirmaTecnicoImage)
        firma_supervisorImage = view.findViewById(R.id.img_firma_supervisor)
        setImageOnClickListener(view, firma_supervisorImage, changeFirmaSupervisorImage)

        val data = this.arguments

        if (data == null) {
            cargarMaquinarias(0)
        } else {
            val id = data.getLong("id", 0)

            if (id > 0) {
                entityId = id
                view.findViewById<LinearLayout>(R.id.layout_checkboxes).visibility = View.VISIBLE
                view.findViewById<LinearLayout>(R.id.layout_firmas).visibility = View.VISIBLE
                cargarAtencion(id)
            }
        }

        return view
    }

    private fun setCrearAtencionOnClickListener(view: View) {
        val button = view.findViewById<Button>(R.id.btn_subir_informe)

        button?.setOnClickListener {

            val numero_solicitud =
                view.findViewById<EditText>(R.id.txt_numero_solicitud).text.toString()
            val spn_maquinaria = view.findViewById<Spinner>(R.id.spn_maquinaria)
            val numero_serie = view.findViewById<EditText>(R.id.txt_numero_serie).text.toString()
            val anho_fabricacion =
                view.findViewById<EditText>(R.id.txt_anho_fabricacion).text.toString()
            val horometro = view.findViewById<EditText>(R.id.txt_horometro).text.toString()
            val stir_2 = view.findViewById<EditText>(R.id.txt_stir_2).text.toString()
            val observaciones = view.findViewById<EditText>(R.id.txt_observaciones).text.toString()

            var mensaje_validacion = ""
            if (numero_solicitud == "") {
                mensaje_validacion += "numero solicitud, "
            }
            if (numero_serie == "") {
                mensaje_validacion += "numero serie, "
            }
            if (anho_fabricacion == "") {
                mensaje_validacion += "anho fabricacion, "
            }
            if (horometro == "") {
                mensaje_validacion += "horometro, "
            }
            if (stir_2 == "") {
                mensaje_validacion += "stir 2"
            }

            if (mensaje_validacion != "") {
                Toast.makeText(
                    context,
                    "Ingrese los siguientes datos:\n ${mensaje_validacion}",
                    Toast.LENGTH_LONG
                ).show()
            } else {
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
                    numero_solicitud,
                    (spn_maquinaria.selectedItem as Maquinaria).id,
                    numero_serie,
                    anho_fabricacion.toInt(),
                    horometro,
                    stir_2,
                    false,
                    false,
                    false,
                    false,
                    observaciones,
                    firma_tecnico,
                    firma_supervisor,
                    fecha_creacion,
                    fecha_modificacion,
                    maquinaria_marca,
                    maquinaria_categoria,
                    maquinaria_modelo,
                    maquinaria_imagen,
                    ""
                )

                if (id == 0) {
                    this.context?.let {
                        AtencionProxy(it).guardar(atencion) { data ->
                            crearAtencionCallback(
                                data
                            )
                        }
                    }
                } else {
                    atencion.id = entityId
                    this.context?.let {
                        AtencionProxy(it).guardar(atencion) { data ->
                            crearAtencionCallback(
                                data
                            )
                        }
                    }
                }
            }

//            val chk_revision_hidraulica = view.findViewById<CheckBox>(R.id.chk_revision_hidraulica)
//            val chk_revision_aceite = view.findViewById<CheckBox>(R.id.chk_revision_aceite)
//            val chk_revision_calibracion =
//                view.findViewById<CheckBox>(R.id.chk_revision_calibracion)
//            val chk_revision_neumaticos = view.findViewById<CheckBox>(R.id.chk_revision_neumaticos)


            //
//            if (chk_revision_hidraulica.isChecked) {
//                val img_revision_hidraulica = view.findViewById<ImageView>(R.id.img_rev_hidraulica)
//                lifecycleScope.launch {
//                    putObject(
//                        BUCKET_NAME,
//                        "${txt_numero_solicitud.text}-revision_hidraulica",
//                        img_revision_hidraulica.getTag(R.id.tag_revision_hidraulica).toString()
//                    )
//                }
//            }
        }
    }

    private fun crearAtencionCallback(atencion: Atencion) {
        view?.findViewById<LinearLayout>(R.id.layout_checkboxes)?.visibility = View.VISIBLE
        view?.findViewById<LinearLayout>(R.id.layout_firmas)?.visibility = View.VISIBLE

        entityId = atencion.id

        Toast.makeText(context, "Se guardó la atención con éxito", Toast.LENGTH_LONG).show()
    }

    private fun cargarAtencion(id: Long) {
        this.context?.let { AtencionProxy(it).obtener(id) { data -> cargarAtencionCallback(data) } }
    }

    private fun cargarAtencionCallback(atencion: Atencion) {
        view?.findViewById<TextView>(R.id.txt_numero_solicitud)?.text = atencion.numero_solicitud
        view?.findViewById<TextView>(R.id.txt_numero_serie)?.text = atencion.numero_serie
        view?.findViewById<TextView>(R.id.txt_anho_fabricacion)?.text =
            atencion.anho_fabricacion.toString()
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

//        val spinner = view?.findViewById<Spinner>(R.id.spn_maquinaria)
//        val adapter = spinner?.adapter

//        for (position in 0 until adapter!!.count) {
//            if (adapter!!.getItemId(position) == atencion.id) {
//                spinner.setSelection(position)
//                return
//            }
//        }
        cargarMaquinarias(atencion.id_maquinaria)

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
                    imageView?.visibility = View.VISIBLE
                }
            }

            // If the URL doesnot point to
            // image or any other kind of failure
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setSpinnerOnItemSelectedListener(view: View) {
        val spinner = view.findViewById<Spinner>(R.id.spn_maquinaria)
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

    private fun cargarMaquinarias(id: Long) {
        this.context?.let {
            MaquinariaProxy(it).listar("") { data ->
                cargarMaquinariasCallback(
                    data,
                    id
                )
            }
        }
    }

    private fun cargarMaquinariasCallback(maquinarias: MutableList<Maquinaria>, id: Long) {
        val spinner = view?.findViewById<Spinner>(R.id.spn_maquinaria)
        val adapter = ArrayAdapter(
            requireActivity().applicationContext,
            android.R.layout.simple_spinner_item,
            maquinarias
        )

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner.
        spinner?.adapter = adapter

        if (id > 0) {
            for (position in 0 until adapter.count) {
                if (adapter.getItem(position)?.id == id) {
                    spinner?.setSelection(position)
                    return
                }
            }
        }
    }

    private fun setImageOnClickListener(
        view: View,
        control: ImageView,
        launcher: ActivityResultLauncher<Intent>
    ) {
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
                revHidraulicaImage.setTag(R.id.tag_revision_hidraulica, imgUri)
                revHidraulicaImage.setImageURI(imgUri)
                val chk = view?.findViewById<CheckBox>(R.id.chk_revision_hidraulica)
                chk?.isChecked = true

                val path: String? = getPath(imgUri!!)
                this.context?.let {
                    AWSUtils(
                        it,
                        path!!,
                        this,
                        AwsConstants.folderPath
                    ).beginUpload()
                }
            }
        }

    private val changeRevAceiteImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                revAceiteImage.setImageURI(imgUri)
                val chk = view?.findViewById<CheckBox>(R.id.chk_revision_aceite)
                chk?.isChecked = true
            }
        }

    private val changeRevCalibracionImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                revCalibracionImage.setImageURI(imgUri)
                val chk = view?.findViewById<CheckBox>(R.id.chk_revision_calibracion)
                chk?.isChecked = true
            }
        }

    private val changeRevNeumaticosImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                revNeumaticosImage.setImageURI(imgUri)
                val chk = view?.findViewById<CheckBox>(R.id.chk_revision_neumaticos)
                chk?.isChecked = true
            }
        }

    private val changeFirmaTecnicoImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                firma_tecnicoImage.setImageURI(imgUri)
            }
        }

    private val changeFirmaSupervisorImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                firma_supervisorImage.setImageURI(imgUri)
            }
        }

    suspend fun putObject(bucketName: String, objectKey: String, objectPath: String) {
        val metadataVal = mutableMapOf<String, String>()
        metadataVal["myVal"] = "test"

        val filePath = Uri.parse(objectPath)
        val inputStream: InputStream? = activity?.contentResolver?.openInputStream(filePath)

        val file = withContext(Dispatchers.IO) {
            File.createTempFile("image", filePath!!.lastPathSegment)
        }

        val outStream: OutputStream =
            withContext(Dispatchers.IO) { FileOutputStream(file) }

        withContext(Dispatchers.IO) {
            outStream.write(inputStream!!.readBytes())
        }

        val baos = ByteArrayOutputStream()
        withContext(Dispatchers.IO) {
            baos.writeTo(outStream)
        }

        //val request =
//            PutObjectRequest {
//                bucket = bucketName
//                key = objectKey
//                metadata = metadataVal
//                //this.body = Paths.get(objectPath).asByteStream()
//                this.body = ByteStream.fromBytes(baos.toByteArray())
//            }

//        AmazonS3Client(
//            BasicAWSCredentials(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY)
//        ).apply {
//            setEndpoint(endpoint).apply {
//                println("S3 endpoint is ${endpoint}")
//            }
//            setS3ClientOptions(
//                S3ClientOptions.builder()
//                    .setPathStyleAccess(true).build()
//            )
//        }

//        S3Client { region = "us-east-2" }.use { s3 ->
//            val response = s3.putObject(request)
//            println("Tag information is ${response.eTag}")
//        }

        //Open gallery to pick image
        openGallery()
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, 100)
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    protected fun getPath(uri: Uri): String? {
        var uri = uri
        var selection: String? = null
        var selectionArgs: Array<String>? = null

        if (DocumentsContract.isDocumentUri(requireActivity().applicationContext, uri)) {
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            } else if (isDownloadsDocument(uri)) {
                try {
                    val id = DocumentsContract.getDocumentId(uri)
                    uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(id)!!
                    )
                } catch (e: NumberFormatException) {
                    return null
                }
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]
                if ("image" == type) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                selection = "_id=?"
                selectionArgs = arrayOf(split[1])
            }
        }
        if ("content".equals(uri.scheme, ignoreCase = true)) {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            var cursor: Cursor? = null
            try {
                cursor = requireActivity().contentResolver.query(
                    uri,
                    projection,
                    selection,
                    selectionArgs,
                    null
                )
                val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    override fun showProgressDialog() {

    }

    override fun hideProgressDialog() {

    }

    override fun onSuccess(imgUrl: String) {

    }

    override fun onError(errorMsg: String) {

    }
}