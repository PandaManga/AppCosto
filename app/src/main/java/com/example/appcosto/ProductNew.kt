package com.example.appcosto

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity




class ProductNew : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_product)
    }

    public fun deployDescuentoDetalles(view: View) {
        var editDescuento: EditText = findViewById(R.id.info_descuento)
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
        var editPromocion: EditText = findViewById(R.id.info_promocion)
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