package com.unimaq.rst.entities

data class Taller (
    val id:Long,
    val latitude:Double,
    val longitud:Double,
    val nombre:String,
    val descripcion:String,
    val activo:Boolean
)