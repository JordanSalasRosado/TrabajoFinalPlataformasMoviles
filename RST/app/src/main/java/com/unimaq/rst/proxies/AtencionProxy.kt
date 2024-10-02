package com.unimaq.rst.proxies

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.unimaq.rst.entities.Atencion
import org.json.JSONException
import org.json.JSONObject

class AtencionProxy {
    private var _context:Context

    constructor(context:Context){
        _context = context
    }

    fun listar(filter: String , callback:(MutableList<Atencion>)-> Unit) {
        val url =
            "https://9hflljc5yc.execute-api.us-east-2.amazonaws.com/default/atencion/listar?filter=$filter"

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
                                obj.getLong("IdMaquinaria"),
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
                                obj.getString("MaquinariaModelo"),
                                obj.getString("MaquinariaImagen")
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

    fun obtener(id: Long , callback:(Atencion)-> Unit) {
        val url =
            "https://9hflljc5yc.execute-api.us-east-2.amazonaws.com/default/atencion/obtener?id=$id"

        val stringRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    val obj = Atencion(
                        response.getLong("Id"),
                        response.getString("NumeroSolicitud"),
                        response.getLong("IdMaquinaria"),
                        response.getString("NumeroSerie"),
                        response.getString("AnhoFabricacion"),
                        response.getString("UltimoMantenimiento"),
                        response.getString("Horometro"),
                        response.getString("Stir2"),
                        response.getBoolean("RevisionHidraulica"),
                        response.getBoolean("RevisionAceite"),
                        response.getBoolean("RevisionCalibracion"),
                        response.getBoolean("RevisionNeumaticos"),
                        response.getString("Observaciones"),
                        response.getString("FirmaTecnico"),
                        response.getString("FirmaSupervisor"),
                        response.getString("FechaCreacion"),
                        response.getString("FechaModificacion"),
                        response.getString("MaquinariaMarca"),
                        response.getString("MaquinariaCategoria"),
                        response.getString("MaquinariaModelo"),
                        response.getString("MaquinariaImagen")
                    )

                    callback(obj)
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

    fun guardar(obj:Atencion){
        var url = "https://9hflljc5yc.execute-api.us-east-2.amazonaws.com/default/atencion"

        val jsonObject = JSONObject()

        try {
               jsonObject.put("NumeroSolicitud",obj.numero_solicitud)
            jsonObject.put("IdMaquinaria",obj.id_maquinaria)
            jsonObject.put("NumeroSerie",obj.numero_serie)
            jsonObject.put("AnhoFabricacion",obj.anho_fabricacion)
            jsonObject.put("UltimoMantenimiento",obj.ultimo_mantenimiento)
            jsonObject.put("Horometro",obj.horometro)
            jsonObject.put("Stir2",obj.stir_2)
            jsonObject.put("RevisionHidraulica",obj.revision_hidraulica)
            jsonObject.put("RevisionAceite",obj.revision_aceite)
            jsonObject.put("RevisionCalibracion",obj.revision_calibracion)
            jsonObject.put("RevisionNeumaticos",obj.revision_neumaticos)
            jsonObject.put("Observaciones",obj.observaciones)
            jsonObject.put("FirmaTecnico",obj.firma_tecnico)
            jsonObject.put("FirmaSupervisor",obj.firma_supervisor)

            val jsonObjReq = object : JsonObjectRequest(
                Request.Method.POST, url, jsonObject,
                Response.Listener { response ->
                    Log.i("======>", "Exito")
                },
                Response.ErrorListener { error: VolleyError ->
                    Log.i("======>", error.message ?: "VolleyError occurred")
                }) {}

            val requestQueue = Volley.newRequestQueue(_context)
            requestQueue.add(jsonObjReq)


        }catch (e: JSONException) {
            Log.i("======>", e.message ?: "JSONException occurred")
        }
    }
}