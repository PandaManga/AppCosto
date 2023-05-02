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
var precioBitmap: Bitmap? = null
var precioBitmapHolder: Bitmap? = null
var productoBitmap: Bitmap? = null
var productoBitmapHolder: Bitmap? = null

class ProductNew : AppCompatActivity() {

    lateinit var popUpFoto: ImageView
    lateinit var precioFotoContenedor: ImageView
    lateinit var productoFotoContenedor: ImageView
    val REQUEST_IMAGE_CAPTURE_PRECIO = 100
    val REQUEST_IMAGE_CAPTURE_PRODUCTO = 105
    lateinit var precioActual : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val MainView = findViewById<View>(R.id.MainView)
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
        botonGuardar.setOnClickListener {
            guardar_producto()

        }

    }

    fun popUpPrecio() {
        val popupDialogPrecio = Dialog(this)
        popupDialogPrecio.setContentView(R.layout.foto_pop_up_precio) // replace with your own custom layout

        val cambiarFoto = popupDialogPrecio.findViewById<Button>(R.id.cambiar_popup)
        val borrarFoto = popupDialogPrecio.findViewById<Button>(R.id.cancelar_borrado)
        val aceptarFoto = popupDialogPrecio.findViewById<Button>(R.id.aceptar_borrado)
        popUpFoto = popupDialogPrecio.findViewById(R.id.foto_contenedor)

        if (precioBitmap == null) {
            popUpFoto.setImageResource(R.drawable.camera_120_128)
        } else {
            popUpFoto.setImageBitmap(precioBitmap)
        }

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
            precioFotoContenedor.setImageResource(R.drawable.camera_120_128)
            precioBitmap = null
            popUpFoto.setImageResource(R.drawable.camera_120_128)
        }
        aceptarFoto.setOnClickListener {
            precioBitmap = precioBitmapHolder
            popupDialogPrecio.dismiss()
        }
        popupDialogPrecio.show()
    }

    fun popUpProducto() {
        val popupDialogProducto = Dialog(this)
        popupDialogProducto.setContentView(R.layout.foto_pop_up_precio) // replace with your own custom layout

        val cambiarFoto = popupDialogProducto.findViewById<Button>(R.id.cambiar_popup)
        val borrarFoto = popupDialogProducto.findViewById<Button>(R.id.cancelar_borrado)
        val aceptarFoto = popupDialogProducto.findViewById<Button>(R.id.aceptar_borrado)
        popUpFoto = popupDialogProducto.findViewById(R.id.foto_contenedor)

        if (productoBitmap == null) {
            popUpFoto.setImageResource(R.drawable.camera_120_128)
        } else {
            popUpFoto.setImageBitmap(productoBitmap)
        }

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
            productoFotoContenedor.setImageResource(R.drawable.camera_120_128)
            popUpFoto.setImageResource(R.drawable.camera_120_128)
            productoBitmap = null
            Toast.makeText(this, "eliminar!", Toast.LENGTH_SHORT).show()
        }
        aceptarFoto.setOnClickListener {
            productoBitmap = productoBitmapHolder
            popupDialogProducto.dismiss()
        }
        popupDialogProducto.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE_PRECIO && resultCode == RESULT_OK) {
            val ImageBitMap = data?.extras?.get("data") as Bitmap
            popUpFoto.setImageBitmap(ImageBitMap)
            precioFotoContenedor.setImageBitmap(ImageBitMap)
            precioBitmapHolder = ImageBitMap
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE_PRODUCTO && resultCode == RESULT_OK) {
            val ImageBitMap = data?.extras?.get("data") as Bitmap
            popUpFoto.setImageBitmap(ImageBitMap)
            productoFotoContenedor.setImageBitmap(ImageBitMap)
            productoBitmapHolder = ImageBitMap
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun guardar_producto() {
        var listoParaGuardar = true
        val infoNombre = findViewById<EditText>(R.id.info_nombreDelProducto)
        val infoMarca = findViewById<EditText>(R.id.info_marca)
        val infoCantidad = findViewById<EditText>(R.id.info_cantidad)
        val infoPrecio = findViewById<EditText>(R.id.info_precio)
        val opcionSiDescuento = findViewById<RadioButton>(R.id.descuento_Si)
        val opcionNoDescuento = findViewById<RadioButton>(R.id.descuento_No)
        val infoDescuento = findViewById<EditText>(R.id.info_descuento)
        val opcionSiPromocion = findViewById<RadioButton>(R.id.promocion_Si)
        val opcionNoPromocion = findViewById<RadioButton>(R.id.promocion_No)
        val infoPromocion = findViewById<EditText>(R.id.info_promocion)
        val infoTienda = findViewById<EditText>(R.id.info_tienda)
        val infoDireccion = findViewById<EditText>(R.id.info_sucursal)

        val ID = sqLiteHelper.get_records_amount()
        val Nombre = validar_datos_vacios(infoNombre)
        val Marca = validar_datos_vacios(infoMarca)
        val Cantidad = validar_datos_vacios(infoCantidad)
        val Precio = validar_numero(infoPrecio)
        val Descuento = validar_opciones(opcionSiDescuento, opcionNoDescuento, infoDescuento)
        val Promocion = validar_opciones(opcionSiPromocion, opcionNoPromocion, infoPromocion)
        val Tienda = validar_datos_vacios(infoTienda)
        val Direccion = validar_datos_vacios(infoDireccion)
        val TieneDescuento = validar_radioButtons(opcionSiDescuento, opcionNoDescuento)
        val TienePromocion = validar_radioButtons(opcionSiPromocion, opcionNoPromocion)

        if (Nombre == "null" || Marca == "null"  || Cantidad == "null" || Precio == 0 ||
            Descuento == "null" || Promocion == "null"  || Tienda == "null" || Direccion == "null")
        {
            listoParaGuardar = false
        }

        if (listoParaGuardar)
        {
            val infoAPasar = ProductModel(
                id = ID + 1,
                nombreProducto = Nombre,
                marca = Marca,
                cantidad = Cantidad,
                precio = Precio,
                tienedescuento = TieneDescuento,
                detalledescuento = Descuento,
                tienepromocion = TienePromocion,
                detallepromocion = Promocion,
                tienda = Tienda,
                direccion = Direccion,
                fotoPrecio = sqLiteHelper.transform_bitmap(precioBitmap),
                fotoProducto = sqLiteHelper.transform_bitmap(productoBitmap)
            )
            val status = sqLiteHelper.insertProduct(infoAPasar)
            if (status > -1)
            {
                reiniciar_fotos()
                Toast.makeText(this, "Producto agregado....", Toast.LENGTH_SHORT).show()
                val Intent = Intent(this, MainActivity::class.java)
                startActivity(Intent)
            }
            else
            {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            Toast.makeText(this, "Favor de llenar todos los campos", Toast.LENGTH_SHORT).show()
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

    public fun validar_datos_vacios(infoVal:EditText): String {

        var datos = infoVal.text.toString()
        if (datos.isEmpty()) {
            Toast.makeText(this, "Favor de llenar los campos", Toast.LENGTH_SHORT).show()
            datos = "null"
        }
        return datos
    }

    fun validar_opciones(opcionSi:RadioButton, opcionNo:RadioButton, infoVal: EditText): String {
        var datos = infoVal.text.toString()
        if(opcionSi.isChecked)
        {
            if(datos.isEmpty())
            {
                Toast.makeText(this, "Favor de llenar los campos", android.widget.Toast.LENGTH_SHORT).show()
                datos = "null"
            }
        }
        else if(opcionNo.isChecked)
        {
            datos = ""
        }
        return datos
    }
    fun validar_radioButtons(opcionSi:RadioButton, opcionNo:RadioButton): Boolean {
        var condicion = false
        if(opcionSi.isChecked)
        {
            condicion = true
        }
        else if(opcionNo.isChecked)
        {
            condicion = false
        }
        return condicion
    }

    fun validar_numero(infoVal:EditText) : Int {
        var datos = infoVal.text.toString().trim()
        var value: Int?
        if (datos.isEmpty()) {
            Toast.makeText(this, "Favor de llenar los campos", android.widget.Toast.LENGTH_SHORT)
                .show()
            value = 0
        }
        else
        {
            value = datos.toIntOrNull()
            if (value == null) {
                Toast.makeText(this, "Favor de colocar un valor sin decimal en Precio", android.widget.Toast.LENGTH_SHORT).show()
                value = 0
            }
            return value
        }
        return value
    }
    fun reiniciar_fotos() {
        precioBitmap = null
        productoBitmap = null
        precioBitmapHolder = null
        productoBitmapHolder = null
    }

}