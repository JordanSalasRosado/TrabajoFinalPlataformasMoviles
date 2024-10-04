package com.unimaq.rst.ui.reportes

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.unimaq.rst.MainActivity
import com.unimaq.rst.R
import com.unimaq.rst.entities.Atencion
import java.util.concurrent.Executors


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
        holder.ultimo_mantenimiento.text = obj.fecha_creacion_formato
        loadImage(holder,holder.img_maquinaria.id, obj.maquinaria_imagen)
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
        val img_maquinaria = view.findViewById<ImageView>(R.id.img_maquinaria)
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = _context.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_activity_main, fragment)

        transaction.commit()
    }

    private fun loadImage(holder : ViewHolder, control:Int, url:String){
        // Declaring and initializing the ImageView
        val imageView = holder.view.findViewById<ImageView>(control)

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
            }

            // If the URL doesnot point to
            // image or any other kind of failure
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}