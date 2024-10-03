package com.unimaq.rst.entities

//data class Maquinaria (
//    val id:Long,
//    val marca:String,
//    val categoria:String,
//    val modelo:String,
//    val imagen:String,
//)

class Maquinaria (
    val id:Long,
    val marca:String,
    val categoria:String,
    val modelo:String,
    val imagen:String,
){
    override fun toString(): String {
        return "${this.marca} - ${this.categoria} ${this.modelo}"
    }
}