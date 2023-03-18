package com.example.appcosto

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity




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

    }


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