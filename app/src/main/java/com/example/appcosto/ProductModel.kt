package com.example.appcosto

import java.util.*

data class ProductModel(
    var id: Int = 0,
    var nombreProducto: String = "",
    var marca: String = "",
    var cantidad: String = "",
    var precio: Int = 14,
    var tienedescuento: Boolean = false,
    var detalledescuento: String = "",
    var tienepromocion: Boolean = false,
    var detallepromocion: String = "",
    var tienda: String = "",
    var direccion : String = ""

)
