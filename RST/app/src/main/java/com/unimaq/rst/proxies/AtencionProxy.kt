package com.unimaq.rst.proxies

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.unimaq.rst.entities.Atencion
import com.unimaq.rst.ui.reportes.ReportesFragment
import org.json.JSONArray
import org.json.JSONException

class AtencionProxy {
    private var _context:Context

    constructor(context:Context){
        _context = context
    }

    fun listar(filter: String , callback:(MutableList<Atencion>)-> Unit) {
        val url =
            "https://9hflljc5yc.execute-api.us-east-2.amazonaws.com/default/atencion?filter=$filter"

        val stringRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    val jsonArray = response
                    Log.i("======>", jsonArray.toString())
                    val items = mutableListOf<Atencion>()
                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        items.add(
                            Atencion(
                                obj.getLong("Id"),
                                obj.getString("NumeroSolicitud"),
                                obj.getString("IdMaquinaria"),
                                obj.getString("NumeroSerie"),
                                obj.getString("AnhoFabricacion"),
                                obj.getString("UltimoMantenimiento"),
                                obj.getString("Horometro"),
                                obj.getString("Stir2"),
                                obj.getBoolean("RevisionHidraulica"),
                                obj.getBoolean("RevisionAceite"),
                                obj.getBoolean("RevisionCalibracion"),
                                obj.getBoolean("RevisionNeumaticos"),
                                obj.getString("Observaciones"),
                                obj.getString("FirmaTecnico"),
                                obj.getString("FirmaSupervisor"),
                                obj.getString("FechaCreacion"),
                                obj.getString("FechaModificacion"),
                                obj.getString("MaquinariaMarca"),
                                obj.getString("MaquinariaCategoria"),
                                obj.getString("MaquinariaModelo")
                            )
                        )
                    }

                    callback(items)
                } catch (e: JSONException) {
                    Log.i("======>", "Error:")
                    Log.i("======>", e.message.toString())
                }
            }, {error->
                Log.i("======>", error.toString())
            })

        val requestQueue = Volley.newRequestQueue(_context)
        requestQueue.add(stringRequest)
    }
}