package com.unimaq.rst.proxies

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.unimaq.rst.entities.Maquinaria
import org.json.JSONException

class MaquinariaProxy {
    private var _context: Context

    constructor(context: Context){
        _context = context
    }

    fun listar(filter: String , callback:(MutableList<Maquinaria>)-> Unit) {
        val url =
            "https://9hflljc5yc.execute-api.us-east-2.amazonaws.com/default/maquinaria/listar?filter=$filter"

        val stringRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    val jsonArray = response
                    Log.i("======>", jsonArray.toString())
                    val items = mutableListOf<Maquinaria>()
                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        items.add(
                            Maquinaria(
                                obj.getLong("Id"),
                                obj.getString("Marca"),
                                obj.getString("Categoria"),
                                obj.getString("Modelo"),
                                obj.getString("Imagen"),
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