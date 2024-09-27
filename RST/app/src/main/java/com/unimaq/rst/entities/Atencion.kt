package com.unimaq.rst.entities

data class Reporte (
    val numero_solicitud:String,
    val maquinaria:String,
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
)