package com.example.appcosto

import android.content.Intent


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addProduct = findViewById<ImageButton>(R.id.addProduct)
        addProduct.setOnClickListener{
            val Intent = Intent(this, ProductNew::class.java)
            startActivity(Intent)
        }

        sqLiteHelper = SQLiteHelper(this)
        val numeroDeRegistros = sqLiteHelper.get_records_amount() //Obtiene el numero de botones a crear
        val numeroDeProductos = numeroDeRegistros + 1
        val nombreDeProductos = sqLiteHelper.get_product_name()
        val IdButtons = create_buttons(numeroDeProductos, addProduct, nombreDeProductos) //El primer registro esta en [1] no en [0]

    }

    fun create_buttons(numeroDeProductos:Int, addButton: ImageButton, nombreDeProductos: MutableList<String>): IntArray {
        var IdButtonsArray = IntArray(numeroDeProductos)
        val Izquierda: Int = 0
        val Derecha:Int = 450
        var TopMargin: Int = 0
        var contador = 1
        while(contador < numeroDeProductos)
        {
            var NumeroPar = EsPar(contador)
            if (NumeroPar)
            {
                TopMargin += 450
                IdButtonsArray[contador] =  CreateNewButton(addButton ,Izquierda, TopMargin, contador, nombreDeProductos)
            }
            else
            {
                IdButtonsArray[contador] =  CreateNewButton(addButton ,Derecha, TopMargin, contador, nombreDeProductos)
            }
            contador += 1

        }
        return IdButtonsArray
    }

    fun CreateNewButton(addButton: ImageButton, StartMargin:Int, TopMargin:Int, contador: Int, nombreDeProductos: MutableList<String>): Int {
        val button = ImageButton(this)
        val layoutParams = ViewGroup.LayoutParams(389, 389)
        val parentLayout = findViewById<ConstraintLayout>(R.id.constraintLayout)
        val constraints = ConstraintSet()
        val contadorNombre = contador - 1
        button.setImageResource(R.drawable.plus)
        button.id = View.generateViewId()
        button.setBackgroundResource(R.drawable.rounded_button)
        button.layoutParams = layoutParams
        var showText = nombreDeProductos[contadorNombre]
        button.setOnClickListener {
            Toast.makeText(this, "You clicked $showText", Toast.LENGTH_SHORT).show()
        }
        parentLayout.addView(button)
        constraints.clone(parentLayout)
        constraints.connect(button.id, ConstraintSet.START, addButton.id, ConstraintSet.START, StartMargin)
        constraints.connect(button.id, ConstraintSet.TOP, addButton.id, ConstraintSet.TOP, TopMargin)
        constraints.applyTo(parentLayout)
        create_text_view(button, parentLayout, contador, showText) //Solo puede contener 16 caracteres antes de que se salga del boton
        return button.id
    }
    fun EsPar(number: Int): Boolean {
        return number % 2 == 0
    }

    fun create_text_view(button: ImageButton, parentLayout : ConstraintLayout, contador: Int, showText: String){
        val textView = TextView(this)
        textView.id = View.generateViewId() // generate a unique id for the textview
        textView.text = showText
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))

        val params = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        params.topToTop = button.id + 50 // set the top constraint of the textview to the top of the image button
        params.bottomToBottom = button.id // set the bottom constraint of the textview to the bottom of the image button
        params.startToStart = button.id // set the start constraint of the textview to the start of the image button
        params.endToEnd = button.id // set the end constraint of the textview to the end of the image button
        textView.layoutParams = params // set the layout parameters of the textview

        val parent = button.parent as ConstraintLayout // get the parent constraint layout of the image button
        parent.addView(textView)
    }

}
