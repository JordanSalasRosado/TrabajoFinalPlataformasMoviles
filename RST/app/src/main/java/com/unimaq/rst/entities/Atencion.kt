package com.unimaq.rst.entities

import java.util.Date

data class Atencion (
    val id:Long,
    val numero_solicitud:String,
    val id_maquinaria:String,
    val numero_serie:String,
    val anho_fabricacion:String,
    val ultimo_mantenimiento:String,
    val horometro:String,
    val stir_2:String,
    val revision_hidraulica:Boolean,
    val revision_aceite:Boolean,
    val revision_calibracion:Boolean,
    val revision_neumaticos:Boolean,
    val observaciones:String,
    val firma_tecnico:String,
    val firma_supervisor:String,
    val fecha_creacion: String,
    val fecha_modificacion: String,
    val maquinaria_marca:String,
    val maquinaria_categoria:String,
    val maquinaria_modelo:String
){
    fun maquinaria():String{
        return "$maquinaria_marca - $maquinaria_categoria $maquinaria_modelo"
    }

}

