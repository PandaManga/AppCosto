package com.example.appcosto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addProduct = findViewById<ImageButton>(R.id.addProduct)
        addProduct.setOnClickListener{
            val Intent = Intent(this, ProductNew::class.java)
            startActivity(Intent)
        }
    }
}