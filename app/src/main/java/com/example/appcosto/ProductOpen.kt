package com.example.appcosto

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

private lateinit var sqLiteHelper: SQLiteHelper
var OpenprecioBitmap: Bitmap? = null
var OpenprecioBitmapHolder: Bitmap? = null
var OldprecioBitmap: Bitmap? = null
var OpenproductoBitmap: Bitmap? = null
var OpenproductoBitmapHolder: Bitmap? = null
var OldproductoBitmap: Bitmap? = null

class ProductOpen: AppCompatActivity() {

    lateinit var popUpFoto: ImageView
    lateinit var precioFotoContenedor: ImageView
    lateinit var productoFotoContenedor: ImageView
    val REQUEST_IMAGE_CAPTURE_PRECIO = 100
    val REQUEST_IMAGE_CAPTURE_PRODUCTO = 105

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_product)
        sqLiteHelper = SQLiteHelper(this)
        val ActualizarProducto = findViewById<Button>(R.id.actualizar_Producto)
        ActualizarProducto.setOnClickListener {
            actualizar_producto()
        }
        setHints()
        setPopUpListeners()
    }

    fun setHints(){
        setName()
        setMarca()
        setCantidad()
        setFotoPrecio()
        setFotoProducto()
        setPrecio()
        setTieneDescuento()
        setTienePromocion()
        setTienda()
        setSucursal()
    }

    fun setName(){
        val NombreView = findViewById<TextView>(R.id.info_nombreDelProducto)
        NombreView.text = sqLiteHelper.get_name(productChosen)
    }

    fun setMarca(){
        val MarcaView = findViewById<TextView>(R.id.info_marca)
        MarcaView.text = sqLiteHelper.get_marca(productChosen)
    }

    fun setCantidad(){
        val CantidadView = findViewById<TextView>(R.id.info_cantidad)
        CantidadView.text = sqLiteHelper.get_cantidad(productChosen)
    }

    fun setFotoPrecio(){
        val FotoPrecioView = findViewById<ImageView>(R.id.info_boton_fotoPrecio)
        OpenprecioBitmapHolder = sqLiteHelper.get_fotoprecio(productChosen)
        OldprecioBitmap = OpenprecioBitmapHolder
        FotoPrecioView.setImageBitmap(OpenprecioBitmapHolder)
    }

    fun setFotoProducto(){
        val FotoProductoView = findViewById<ImageView>(R.id.info_boton_fotoProducto)
        OpenproductoBitmapHolder = sqLiteHelper.get_fotoproducto(productChosen)
        OldproductoBitmap = OpenproductoBitmapHolder
        FotoProductoView.setImageBitmap(OpenproductoBitmapHolder)
    }

    fun setPrecio(){
        val PrecioView = findViewById<TextView>(R.id.info_precio)
        PrecioView.text = sqLiteHelper.get_precio(productChosen).toString()
    }

    fun setTieneDescuento(){
        val TieneDescuento = sqLiteHelper.get_tienedescuento(productChosen)
        val radioSi = findViewById<RadioButton>(R.id.descuento_Si)
        val radioNo = findViewById<RadioButton>(R.id.descuento_No)
        if (TieneDescuento == true)
        {
            radioSi.isChecked = true
            deployDescuentoDetallesAut(this)
            setdetallesDescuento()
        }
        else
        {
            radioNo.isChecked = true
        }
    }

    fun setTienePromocion(){
        val TienePromocion = sqLiteHelper.get_tienepromocion(productChosen)
        val radioSi = findViewById<RadioButton>(R.id.promocion_Si)
        val radioNo = findViewById<RadioButton>(R.id.promocion_No)
        if (TienePromocion == true)
        {
            radioSi.isChecked = true
            deployPromocionDetallesAut(this)
            setdetallesPromocion()
        }
        else
        {
            radioNo.isChecked = true
        }
    }

    fun setdetallesPromocion(){
        val DetalleView = findViewById<TextView>(R.id.info_promocion)
        DetalleView.text = sqLiteHelper.get_descuento_detalle(productChosen)
    }

    fun setdetallesDescuento(){
        val DetalleView = findViewById<TextView>(R.id.info_descuento)
        DetalleView.text = sqLiteHelper.get_promocion_detalle(productChosen)
    }

    fun setTienda(){
        val TiendaView = findViewById<TextView>(R.id.info_tienda)
        TiendaView.text = sqLiteHelper.get_tienda(productChosen)
    }

    fun setSucursal(){
        val SucursalView = findViewById<TextView>(R.id.info_sucursal)
        SucursalView.text = sqLiteHelper.get_sucursal(productChosen)
    }

    fun popUpPrecio() {
        val popupDialogPrecio = Dialog(this)
        popupDialogPrecio.setContentView(R.layout.foto_pop_up_precio) // replace with your own custom layout

        val cambiarFoto = popupDialogPrecio.findViewById<Button>(R.id.cambiar_popup)
        val borrarFoto = popupDialogPrecio.findViewById<Button>(R.id.cancelar_borrado)
        val aceptarFoto = popupDialogPrecio.findViewById<Button>(R.id.aceptar_borrado)
        popUpFoto = popupDialogPrecio.findViewById(R.id.foto_contenedor)

        if (OpenprecioBitmap == null) {
            popUpFoto.setImageBitmap(OldprecioBitmap)
        } else {
            popUpFoto.setImageBitmap(OpenprecioBitmapHolder)
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
            precioFotoContenedor.setImageBitmap(OldprecioBitmap)
            OpenprecioBitmap = null
            OpenprecioBitmapHolder = OldprecioBitmap
            popUpFoto.setImageBitmap(OldprecioBitmap)
        }
        aceptarFoto.setOnClickListener {
            OpenprecioBitmap = OpenprecioBitmapHolder
            popupDialogPrecio.dismiss()
        }
        popupDialogPrecio.show()
    }

    fun popUpProducto() {
        val popupDialogProducto = Dialog(this)
        popupDialogProducto.setContentView(R.layout.foto_pop_up_producto) // replace with your own custom layout

        val cambiarFoto = popupDialogProducto.findViewById<Button>(R.id.cambiar_popup)
        val borrarFoto = popupDialogProducto.findViewById<Button>(R.id.cancelar_borrado)
        val aceptarFoto = popupDialogProducto.findViewById<Button>(R.id.aceptar_borrado)
        popUpFoto = popupDialogProducto.findViewById(R.id.foto_contenedor)

        if (OpenproductoBitmap == null) {
            popUpFoto.setImageBitmap(OldproductoBitmap)
        } else {
            popUpFoto.setImageBitmap(OpenproductoBitmapHolder)
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
            Toast.makeText(this, "eliminar!", Toast.LENGTH_SHORT).show()
            productoFotoContenedor.setImageBitmap(OldproductoBitmap)
            OpenproductoBitmap = null
            OpenproductoBitmapHolder = OldproductoBitmap
            popUpFoto.setImageBitmap(OldproductoBitmap)
        }
        aceptarFoto.setOnClickListener {
            OpenproductoBitmap = OpenproductoBitmapHolder
            popupDialogProducto.dismiss()
        }
        popupDialogProducto.show()
    }

    fun popUpConfirmarBorrar() {
        val popUpConfirmarBorrar = Dialog(this)
        popUpConfirmarBorrar.setContentView(R.layout.pop_up_borrar_confirmar) // replace with your own custom layout

        val regresar = popUpConfirmarBorrar.findViewById<Button>(R.id.cancelar_borrado)
        val borrarProducto = popUpConfirmarBorrar.findViewById<Button>(R.id.aceptar_borrado)
        
        regresar.setOnClickListener {
            popUpConfirmarBorrar.dismiss()
        }
        borrarProducto.setOnClickListener {
            sqLiteHelper.borrarProducto(productChosen)
            popUpConfirmarBorrar.dismiss()
            val Intent = Intent(this, MainActivity::class.java)
            startActivity(Intent)

        }
        popUpConfirmarBorrar.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE_PRECIO && resultCode == RESULT_OK) {
            val ImageBitMap = data?.extras?.get("data") as Bitmap
            popUpFoto.setImageBitmap(ImageBitMap)
            precioFotoContenedor.setImageBitmap(ImageBitMap)
            OpenprecioBitmapHolder = ImageBitMap
        }
       if (requestCode == REQUEST_IMAGE_CAPTURE_PRODUCTO && resultCode == RESULT_OK) {
           val ImageBitMap = data?.extras?.get("data") as Bitmap
           popUpFoto.setImageBitmap(ImageBitMap)
           productoFotoContenedor.setImageBitmap(ImageBitMap)
           OpenproductoBitmapHolder = ImageBitMap
       }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun setPopUpListeners(){
        precioFotoContenedor = findViewById(R.id.info_boton_fotoPrecio)
        productoFotoContenedor = findViewById(R.id.info_boton_fotoProducto)
        val borrarProducto = findViewById<Button>(R.id.borrar_producto)
        precioFotoContenedor.setOnClickListener {
            popUpPrecio()
        }
        productoFotoContenedor.setOnClickListener {
            popUpProducto()
        }
        borrarProducto.setOnClickListener {
            popUpConfirmarBorrar()
        }
    }

    fun deployDescuentoDetalles(view: View) {
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
    fun deployPromocionDetalles(view: View) {
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

    fun deployDescuentoDetallesAut(view: ProductOpen) {
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
    fun deployPromocionDetallesAut(view: ProductOpen) {
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

    fun validar_datos_vacios(infoVal:EditText): String {

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

    private fun actualizar_producto() {
        if (OpenprecioBitmap == null){
            OpenprecioBitmap = OldprecioBitmap
        }
        else if (OpenproductoBitmap == null) {
            OpenproductoBitmap = OldproductoBitmap
        }

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

        val ID = productChosen
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
                id = ID,
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
                fotoPrecio = sqLiteHelper.transform_bitmap(OpenprecioBitmap),
                fotoProducto = sqLiteHelper.transform_bitmap(OpenproductoBitmap)
            )
            val status = sqLiteHelper.updateProduct(infoAPasar, productChosen)
            if (status > -1)
            {
                reiniciar_fotos()
                Toast.makeText(this, "Producto actualizado....", Toast.LENGTH_SHORT).show()
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

    fun reiniciar_fotos(){
        OpenprecioBitmap = null
        OpenprecioBitmapHolder = null
        OldprecioBitmap = null
        OpenproductoBitmap = null
        OpenproductoBitmapHolder = null
        OldproductoBitmap = null
    }

}