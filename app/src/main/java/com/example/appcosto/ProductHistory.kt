package com.example.appcosto

data class ProductHistory (
    var id: Int,
    var nombre: String = "",
    var precio: Int,
    var tienedescuento: Boolean = false,
    var detalledescuento: String = "",
    var tienepromocion: Boolean = false,
    var detallepromocion: String = "",
    var fecha: String = ""
)

