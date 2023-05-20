package com.example.appcosto


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATEBASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_VERSION = 1
        private const val DATEBASE_NAME = "products.db"
        private const val TABLE_NAME = "table_products"
        private const val IDproducto = "IDproducto"
        private const val NombreProducto = "NombreProducto"
        private const val Marca = "Marca"
        private const val Cantidad = "Cantidad"
        private const val FotoPrecio = "FotoPrecio"
        private const val FotoProducto = "FotoProducto"
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
                + FotoProducto + " BLOB," + FotoPrecio + " BLOB,"
                + TieneDescuento + " BOOLEAN," + DetalleDescuento + " TEXT,"
                + TienePromocion + " BOOLEAN," + DetallePromocion + " TEXT,"
                + Tienda + " TEXT," + Direccion + " TEXT" + ")"
                )

        val createTableHistory = ("CREATE TABLE products_history ("
                + SQLiteHelper.IDproducto + " INTEGER," + SQLiteHelper.NombreProducto + " TEXT,"
                + SQLiteHelper.Precio + " INTEGER," + SQLiteHelper.TieneDescuento + " BOOLEAN,"
                + SQLiteHelper.TienePromocion + " BOOLEAN," + " Fecha TEXT" + ")"
                )
        db?.execSQL(createTableHistory)
        db?.execSQL(createTableProductos)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS products_history")
        onCreate(db)
    }
    fun updateProduct(std: ProductModel, ID: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        val selection = "IDproducto = ?"
        val selectionArgs = arrayOf("$ID")
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
        contentValues.put(FotoPrecio, std.fotoPrecio)
        contentValues.put(FotoProducto, std.fotoProducto)

        val success = db.update(TABLE_NAME,  contentValues, selection, selectionArgs)
        db.close()
        return success
    }

    fun insertProduct(std: ProductModel, ): Long {
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
        contentValues.put(FotoPrecio, std.fotoPrecio)
        contentValues.put(FotoProducto, std.fotoProducto)

        val success = db.insert(TABLE_NAME, null, contentValues)

        db.close()
        return success

    }

    fun borrarProducto(ID: Int): Int {
        val db = this.writableDatabase
        val selection = "IDproducto = ?"
        val selectionArgs = arrayOf("$ID")
        val result = db.delete(TABLE_NAME, selection, selectionArgs)
        return result
    }

    fun get_min_value(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT MIN($IDproducto) FROM $TABLE_NAME", null)
        var minValue = 0

        if (cursor.moveToFirst()) {
            minValue = cursor.getInt(0)
        }

        cursor.close()
        return minValue
    }

    fun get_records_amount(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_NAME", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count
    }

    fun get_product_name(): MutableList<String> {
        val db = readableDatabase

        val resultArray = mutableListOf<String>()
        val projection =
            arrayOf(NombreProducto)
        val sortOrder = null

        val cursor: Cursor = db.query(
            TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )// get cursor object
        val columnIndex = cursor.getColumnIndex("NombreProducto")

        while (cursor.moveToNext()) {
            val columnValue = cursor.getString(columnIndex)
            resultArray.add(columnValue)
        }
        cursor.close()
        return resultArray
    }

    fun transform_bitmap(bitmap: Bitmap?): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        }
        val byteArray = byteArrayOutputStream.toByteArray()
        return byteArray
    }

    fun get_images_product(): MutableList<Bitmap> {
        val db = readableDatabase

        val resultArray = mutableListOf<Bitmap>()
        val projection =
            arrayOf(NombreProducto)
        val sortOrder = null
        val query = "SELECT FotoProducto FROM table_products"
        val cursor = db.rawQuery(query, null)
        val columnIndex = cursor.getColumnIndex("FotoProducto")

        while (cursor.moveToNext()) {
            val columnValue = cursor.getBlob(columnIndex)
            val bitmap = BitmapFactory.decodeByteArray(columnValue, 0, columnValue.size)
            resultArray.add(bitmap)
        }
        cursor.close()
        return resultArray
    }


    fun get_name(ID: Int): String{
        val db = readableDatabase
        val columns = arrayOf("NombreProducto")
        val selection = "IDproducto = ?"
        val selectionArgs = arrayOf("$ID")
        val cursor = db.query("table_products", columns, selection, selectionArgs, null, null, null)
        var name: String = "null"
        val columnIndex = cursor.getColumnIndex("NombreProducto")
        if (cursor.moveToFirst()) {
            name = cursor.getString(columnIndex)
        }
        cursor.close()
        db.close()
        return name
    }

    fun get_marca(ID: Int): String {
        val db = readableDatabase
        val columns = arrayOf("Marca")
        val selection = "IDproducto = ?"
        val selectionArgs = arrayOf("$ID")
        val cursor = db.query("table_products", columns, selection, selectionArgs, null, null, null)
        var marca: String = "null"
        val columnIndex = cursor.getColumnIndex("Marca")
        if (cursor.moveToFirst()) {
            marca = cursor.getString(columnIndex)
        }
        cursor.close()
        db.close()
        return marca
    }

    fun get_cantidad(ID: Int): String {
        val db = readableDatabase
        val columns = arrayOf("Cantidad")
        val selection = "IDproducto = ?"
        val selectionArgs = arrayOf("$ID")
        val cursor = db.query("table_products", columns, selection, selectionArgs, null, null, null)
        var cantidad: String = "null"
        val columnIndex = cursor.getColumnIndex("Cantidad")
        if (cursor.moveToFirst()) {
            cantidad = cursor.getString(columnIndex)
        }
        cursor.close()
        db.close()
        return cantidad
    }

    fun get_precio(ID: Int): Int {
        val db = readableDatabase
        val columns = arrayOf("Precio")
        val selection = "IDproducto = ?"
        val selectionArgs = arrayOf("$ID")
        val cursor = db.query("table_products", columns, selection, selectionArgs, null, null, null)
        var precio: Int = 0
        val columnIndex = cursor.getColumnIndex("Precio")
        if (cursor.moveToFirst()) {
            precio = cursor.getInt(columnIndex)
        }
        cursor.close()
        db.close()
        return precio

    }

    fun get_fotoproducto(ID: Int): Bitmap? {
        val db = readableDatabase
        val columns = arrayOf("FotoProducto")
        val selection = "IDproducto = ?"
        var fotoBitmap: Bitmap? = null
        val selectionArgs = arrayOf("$ID")
        val cursor = db.query("table_products", columns, selection, selectionArgs, null, null, null)
        var foto: ByteArray? = null
        val columnIndex = cursor.getColumnIndex("FotoProducto")
        if (cursor.moveToFirst()) {
            foto = cursor.getBlob(columnIndex)
            fotoBitmap = BitmapFactory.decodeByteArray(foto, 0, foto.size)
        }
        cursor.close()
        db.close()
        return fotoBitmap
    }

    fun get_fotoprecio(ID: Int):  Bitmap? {
        val db = readableDatabase
        val columns = arrayOf("FotoPrecio")
        val selection = "IDproducto = ?"
        var fotoBitmap: Bitmap? = null
        val selectionArgs = arrayOf("$ID")
        val cursor = db.query("table_products", columns, selection, selectionArgs, null, null, null)
        var foto: ByteArray? = null
        val columnIndex = cursor.getColumnIndex("FotoPrecio")
        if (cursor.moveToFirst()) {
            foto = cursor.getBlob(columnIndex)
            fotoBitmap = BitmapFactory.decodeByteArray(foto, 0, foto.size)
        }
        cursor.close()
        db.close()
        return fotoBitmap
    }

    fun get_tienedescuento(ID: Int):Boolean? {
        val db = readableDatabase
        val columns = arrayOf("TieneDescuento")
        val selection = "IDproducto = ?"
        val selectionArgs = arrayOf("$ID")
        val cursor = db.query("table_products", columns, selection, selectionArgs, null, null, null)
        var isActive: Boolean? = null
        val columnIndex = cursor.getColumnIndex("TieneDescuento")
        if (cursor.moveToFirst()) {
            isActive = cursor.getInt(columnIndex) != 0
        }
        cursor.close()
        db.close()
        return isActive
    }

    fun get_tienepromocion(ID: Int):Boolean? {
        val db = readableDatabase
        val columns = arrayOf("TienePromocion")
        val selection = "IDproducto = ?"
        val selectionArgs = arrayOf("$ID")
        val cursor = db.query("table_products", columns, selection, selectionArgs, null, null, null)
        var isActive: Boolean? = null
        val columnIndex = cursor.getColumnIndex("TienePromocion")
        if (cursor.moveToFirst()) {
            isActive = cursor.getInt(columnIndex) != 0
        }
        cursor.close()
        db.close()
        return isActive
    }

    fun get_promocion_detalle(ID: Int): String {
        val db = readableDatabase
        val columns = arrayOf("DetallePromocion")
        val selection = "IDproducto = ?"
        val selectionArgs = arrayOf("$ID")
        val cursor = db.query("table_products", columns, selection, selectionArgs, null, null, null)
        var detalle: String = "null"
        val columnIndex = cursor.getColumnIndex("DetallePromocion")
        if (cursor.moveToFirst()) {
            detalle = cursor.getString(columnIndex)
        }
        cursor.close()
        db.close()
        return detalle
    }

    fun get_descuento_detalle(ID: Int): String {
        val db = readableDatabase
        val columns = arrayOf("DetalleDescuento")
        val selection = "IDproducto = ?"
        val selectionArgs = arrayOf("$ID")
        val cursor = db.query("table_products", columns, selection, selectionArgs, null, null, null)
        var detalle: String = "null"
        val columnIndex = cursor.getColumnIndex("DetalleDescuento")
        if (cursor.moveToFirst()) {
            detalle = cursor.getString(columnIndex)
        }
        cursor.close()
        db.close()
        return detalle
    }


    fun get_tienda(ID: Int): String {
        val db = readableDatabase
        val columns = arrayOf("Tienda")
        val selection = "IDproducto = ?"
        val selectionArgs = arrayOf("$ID")
        val cursor = db.query("table_products", columns, selection, selectionArgs, null, null, null)
        var tienda: String = "null"
        val columnIndex = cursor.getColumnIndex("Tienda")
        if (cursor.moveToFirst()) {
            tienda = cursor.getString(columnIndex)
        }
        cursor.close()
        db.close()
        return tienda
    }

    fun get_sucursal(ID: Int) : String {
        val db = readableDatabase
        val columns = arrayOf("Direccion")
        val selection = "IDproducto = ?"
        val selectionArgs = arrayOf("$ID")
        val cursor = db.query("table_products", columns, selection, selectionArgs, null, null, null)
        var sucursal: String = "null"
        val columnIndex = cursor.getColumnIndex("Direccion")
        if (cursor.moveToFirst()) {
            sucursal = cursor.getString(columnIndex)
        }
        cursor.close()
        db.close()
        return sucursal
    }
}