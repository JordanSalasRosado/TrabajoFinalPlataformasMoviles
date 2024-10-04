package com.unimaq.rst.ui.ubicaciones

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.unimaq.rst.R
import com.unimaq.rst.entities.Maquinaria
import com.unimaq.rst.entities.Taller
import com.unimaq.rst.proxies.MaquinariaProxy
import com.unimaq.rst.proxies.TallerProxy

class UbicacionesFragment : Fragment() {
    private lateinit var map: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        map = googleMap
        val upc = LatLng(-12.103545016356927, -76.96291401963215)
        googleMap.addMarker(MarkerOptions().position(upc).title("Marker in UPC"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(upc, 10F))
        //googleMap.maxZoomLevel

//        googleMap.uiSettings.isZoomControlsEnabled = true
//        googleMap.isTrafficEnabled = true
//        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
//        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
//        googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
//        googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN

        this.context?.let {
            TallerProxy(it).listar("") { data ->
                cargarMaquinariasCallback(data)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bar = (activity as AppCompatActivity?)!!.supportActionBar
        bar?.title = "Ubicaciones"
        return inflater.inflate(R.layout.fragment_ubicaciones, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun cargarMaquinariasCallback(talleres: MutableList<Taller>) {
        for (position in 0 until talleres.count()) {
            val taller = talleres.get(position)
            val location = LatLng(taller.latitude, taller.longitud)
            map.addMarker(
                MarkerOptions()
                    .position(location)
                    .title(taller.nombre)
                    .snippet(taller.descripcion)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
        }
    }
}