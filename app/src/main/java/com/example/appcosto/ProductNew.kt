package com.example.appcosto

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


private lateinit var sqLiteHelper: SQLiteHelper

class ProductNew : AppCompatActivity() {

    lateinit var popUpFoto: ImageView
    lateinit var precioFotoContenedor: ImageView
    lateinit var productoFotoContenedor: ImageView
    val REQUEST_IMAGE_CAPTURE_PRECIO = 100
    val REQUEST_IMAGE_CAPTURE_PRODUCTO = 105


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_product)
        precioFotoContenedor = findViewById(R.id.info_boton_fotoPrecio)
        productoFotoContenedor = findViewById(R.id.info_boton_fotoProducto)

        precioFotoContenedor.setOnClickListener {
            popUpPrecio()
        }

        productoFotoContenedor.setOnClickListener {
            popUpProducto()
        }

        sqLiteHelper = SQLiteHelper(this)
        val botonGuardar = findViewById<Button>(R.id.guardar_producto)
        botonGuardar.setOnClickListener { guardar_producto()}

    }

    fun popUpPrecio() {
        val popupDialogPrecio = Dialog(this)
        popupDialogPrecio.setContentView(R.layout.foto_pop_up_precio) // replace with your own custom layout

        val cambiarFoto = popupDialogPrecio.findViewById<Button>(R.id.cambiar_popup)
        val borrarFoto = popupDialogPrecio.findViewById<Button>(R.id.eliminar_popup)
        val aceptarFoto = popupDialogPrecio.findViewById<Button>(R.id.aceptar_popup)
        popUpFoto = popupDialogPrecio.findViewById(R.id.foto_contenedor)


        cambiarFoto.setOnClickListener {
            Toast.makeText(this, "cambiar!", Toast.LENGTH_SHORT).show()
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_PRECIO)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Error: " + e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
        borrarFoto.setOnClickListener {
            Toast.makeText(this, "eliminar!", Toast.LENGTH_SHORT).show()
        }
        aceptarFoto.setOnClickListener {
            popupDialogPrecio.dismiss()
        }
        popupDialogPrecio.show()
    }

    fun popUpProducto() {
        val popupDialogProducto = Dialog(this)
        popupDialogProducto.setContentView(R.layout.foto_pop_up_precio) // replace with your own custom layout

        val cambiarFoto = popupDialogProducto.findViewById<Button>(R.id.cambiar_popup)
        val borrarFoto = popupDialogProducto.findViewById<Button>(R.id.eliminar_popup)
        val aceptarFoto = popupDialogProducto.findViewById<Button>(R.id.aceptar_popup)
        popUpFoto = popupDialogProducto.findViewById(R.id.foto_contenedor)

        cambiarFoto.setOnClickListener {
            Toast.makeText(this, "cambiar!", Toast.LENGTH_SHORT).show()
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_PRODUCTO)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Error: " + e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
        borrarFoto.setOnClickListener {
            Toast.makeText(this, "eliminar!", Toast.LENGTH_SHORT).show()
        }
        aceptarFoto.setOnClickListener {
            popupDialogProducto.dismiss()
        }
        popupDialogProducto.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE_PRECIO && resultCode == RESULT_OK) {
            val ImageBitMap = data?.extras?.get("data") as Bitmap
            popUpFoto.setImageBitmap(ImageBitMap)
            precioFotoContenedor.setImageBitmap(ImageBitMap)
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE_PRODUCTO && resultCode == RESULT_OK) {
            val ImageBitMap = data?.extras?.get("data") as Bitmap
            popUpFoto.setImageBitmap(ImageBitMap)
            productoFotoContenedor.setImageBitmap(ImageBitMap)
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
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