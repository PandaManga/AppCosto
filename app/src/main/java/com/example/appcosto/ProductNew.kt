package com.example.appcosto

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity


private lateinit var sqLiteHelper: SQLiteHelper

class ProductNew : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_product)
        val popupDialog = Dialog(this)
        val popupDialog2 = Dialog(this)
        popupDialog.setContentView(R.layout.foto_pop_up_producto)
        popupDialog.setTitle("Popup Title")
        popupDialog.setCancelable(true)

        popupDialog2.setContentView(R.layout.foto_pop_up_precio)
        popupDialog2.setTitle("Popup Title")
        popupDialog2.setCancelable(true)

        val popupPrecio = findViewById<ImageButton>(R.id.info_boton_fotoPrecio)
        popupPrecio.setOnClickListener { popupDialog.show() }

        val popupProducto = findViewById<ImageButton>(R.id.info_boton_fotoProducto)
        popupProducto.setOnClickListener { popupDialog2.show() }

        sqLiteHelper = SQLiteHelper(this)
        val botonGuardar = findViewById<Button>(R.id.guardar_producto)
        botonGuardar.setOnClickListener { guardar_producto()}

    }

    private fun guardar_producto() {
        val nombreProducto = "Test-nombreProducto"
        val marca = "Test-marca"
        val cantidad = "Test-cantidad"
        val precio = 59
        val tienedescuento = false
        val detalledescuento = "Test-null"
        val tienepromocion = true
        val detallepromocion = "Test-2x1"
        val tienda = "Test-tienda"
        val direccion = "Test-direccion"

        val infoAPasar = ProductModel(
            nombreProducto = nombreProducto,
            marca = marca,
            cantidad = cantidad,
            precio = precio,
            tienedescuento = tienedescuento,
            detalledescuento =  detalledescuento,
            tienepromocion = tienepromocion,
            detallepromocion = detallepromocion,
            tienda = tienda,
            direccion = direccion
            )

        val status = sqLiteHelper.insertProduct(infoAPasar)

        if (status > -1) {
            Toast.makeText(this, "Producto agregado....", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
        }



    }

    //Hace display de los detalles de acuerdo al radiobutton seleccionado
    public fun deployDescuentoDetalles(view: View) {
        val editDescuento: EditText = findViewById(R.id.info_descuento)
        val descuentoSi = findViewById<RadioButton>(R.id.descuento_Si)
        val descuentoNo = findViewById<RadioButton>(R.id.descuento_No)



        if(descuentoSi.isChecked)
        {
            editDescuento.visibility = View.VISIBLE
        }
        else if(descuentoNo.isChecked)
        {
            editDescuento.visibility = View.GONE
        }

    }

    //Hace display de los detalles de acuerdo al radiobutton seleccionado
    public fun deployPromocionDetalles(view: View) {
        val editPromocion: EditText = findViewById(R.id.info_promocion)
        val promocionSi = findViewById<RadioButton>(R.id.promocion_Si)
        val promocionNo = findViewById<RadioButton>(R.id.promocion_No)
        if(promocionSi.isChecked)
        {
            editPromocion.visibility = View.VISIBLE
        }
        else if(promocionNo.isChecked)
        {
            editPromocion.visibility = View.GONE
        }

    }




}