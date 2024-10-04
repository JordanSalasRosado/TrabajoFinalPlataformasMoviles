package com.unimaq.rst.proxies

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.unimaq.rst.entities.Maquinaria
import com.unimaq.rst.entities.Taller
import org.json.JSONException

class TallerProxy {
    private var _context: Context

    constructor(context: Context){
        _context = context
    }

    fun listar(filter: String , callback:(MutableList<Taller>)-> Unit) {
        val url =
            "https://9hflljc5yc.execute-api.us-east-2.amazonaws.com/default/taller/listar?filter=$filter"

        val stringRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    val jsonArray = response
                    Log.i("======>", jsonArray.toString())
                    val items = mutableListOf<Taller>()
                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        items.add(
                            Taller(
                                obj.getLong("Id"),
                                obj.getDouble("Latitud"),
                                obj.getDouble("Longitud"),
                                obj.getString("Nombre"),
                                obj.getString("Descripcion"),
                                obj.getBoolean("Activo")
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