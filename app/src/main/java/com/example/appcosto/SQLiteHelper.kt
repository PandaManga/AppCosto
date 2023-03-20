package com.example.appcosto

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATEBASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_VERSION = 1
        private const val DATEBASE_NAME = "products.db"
        private const val TABLE_NAME = "table_products"
        private const val IDproducto = "IDproducto"
        private const val NombreProducto = "NombreProducto"
        private const val Marca = "Marca"
        private const val Cantidad = "Cantidad"
        private const val fotoPrecio = "fotoPrecio"
        private const val fotoProducto = "fotoProducto"
        private const val Precio = "Precio"
        private const val TieneDescuento = "TieneDescuento"
        private const val DetalleDescuento = "DetalleDescuento"
        private const val TienePromocion = "TienePromocion"
        private const val DetallePromocion = "DetallePromocion"
        private const val Tienda = "Tienda"
        private const val Direccion = "Direccion"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableProductos = ("CREATE TABLE " + TABLE_NAME + "("
                + IDproducto + " INTEGER PRIMARY_KEY," + NombreProducto + " TEXT,"
                + Marca + " TEXT," + Cantidad + " TEXT," + Precio + " INTEGER,"
                + TieneDescuento + " BOOLEAN," + DetalleDescuento + " TEXT,"
                + TienePromocion + " BOOLEAN," + DetallePromocion + " TEXT,"
                + Tienda + " TEXT," + Direccion + " TEXT" + ")"
                )
        db?.execSQL(createTableProductos)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertProduct(std: ProductModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(IDproducto, std.id)
        contentValues.put(NombreProducto, std.nombreProducto)
        contentValues.put(Marca, std.marca)
        contentValues.put(Cantidad, std.cantidad)
        contentValues.put(Precio, std.precio)
        contentValues.put(TieneDescuento, std.tienedescuento)
        contentValues.put(DetalleDescuento, std.detalledescuento)
        contentValues.put(TienePromocion, std.tienepromocion)
        contentValues.put(DetallePromocion, std.detallepromocion)
        contentValues.put(Tienda, std.tienda)
        contentValues.put(Direccion, std.direccion)

        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return success

    }

}