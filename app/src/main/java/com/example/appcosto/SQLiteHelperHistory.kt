package com.example.appcosto

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SQLiteHelperHistory(context: Context) : SQLiteOpenHelper(context, DATEBASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_VERSION = 1
        private const val DATEBASE_NAME = "products.db"
        private const val TABLE_NAME = "products_history"
        private const val IDproducto = "IDproducto"
        private const val NombreProducto = "NombreProducto"
        private const val Precio = "Precio"
        private const val TieneDescuento = "TieneDescuento"
        private const val TienePromocion = "TienePromocion"
        private const val Fecha = "Fecha"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableHistory = ("CREATE TABLE " + SQLiteHelperHistory.TABLE_NAME + "("
                + SQLiteHelperHistory.IDproducto + " INTEGER," + SQLiteHelperHistory.NombreProducto + " TEXT,"
                + SQLiteHelperHistory.Precio + " INTEGER," + SQLiteHelperHistory.TieneDescuento + " BOOLEAN,"
                + SQLiteHelperHistory.TienePromocion + " BOOLEAN," + SQLiteHelperHistory.Fecha + " TEXT" + ")"
                )
        db?.execSQL(createTableHistory)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS ${SQLiteHelperHistory.TABLE_NAME}")
        onCreate(db)
    }

    fun insertHistory(std:ProductHistory, ): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(SQLiteHelperHistory.IDproducto, std.id)
        contentValues.put(SQLiteHelperHistory.NombreProducto, std.nombre)
        contentValues.put(SQLiteHelperHistory.Precio, std.precio)
        contentValues.put(SQLiteHelperHistory.TieneDescuento, std.tienedescuento)
        contentValues.put(SQLiteHelperHistory.TienePromocion, std.tienepromocion)
        contentValues.put(SQLiteHelperHistory.Fecha, std.fecha)

        val success = db.insert(SQLiteHelperHistory.TABLE_NAME, null, contentValues)

        db.close()
        return success

    }

    fun borrarHistory(ID: Int): Int {
        val db = this.writableDatabase
        val selection = "IDproducto = ?"
        val selectionArgs = arrayOf("$ID")
        val result = db.delete(SQLiteHelperHistory.TABLE_NAME, selection, selectionArgs)
        return result
    }

    fun getLocalDate(): String  {
            val currentDate = LocalDate.now()
            val formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            return formattedDate
    }

    fun getStoredDates(producto: Int): MutableList<String> {
        val db = readableDatabase
        val resultArray = mutableListOf<String>()
        val selectQuery = "SELECT $Fecha FROM $TABLE_NAME WHERE $IDproducto = $producto"
        val cursor = db.rawQuery(selectQuery, null)
        val columnIndex = cursor.getColumnIndex("Fecha")
        while (cursor.moveToNext()) {
            val data = cursor.getString(columnIndex)
            resultArray.add(data)
        }
        cursor?.close()
        db.close()
        return resultArray
    }

    fun getIDcount(producto: Int): Int{
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM ${SQLiteHelperHistory.TABLE_NAME} WHERE $IDproducto = $producto", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count
    }

    fun getPrices(producto: Int): MutableList<Int> {
        val db = readableDatabase
        val resultArray = mutableListOf<Int>()
        val selectQuery = "SELECT $Precio FROM $TABLE_NAME WHERE $IDproducto = $producto"
        val cursor = db.rawQuery(selectQuery, null)
        val columnIndex = cursor.getColumnIndex("Precio")
        while (cursor.moveToNext()) {
            val data = cursor.getInt(columnIndex)
            resultArray.add(data)
        }
        cursor?.close()
        db.close()
        return resultArray
    }

    fun deleteHistory(producto: Int) {
        val db = writableDatabase
        val filter = "IDproducto = $producto"
        val rowsDeleted = db.delete(TABLE_NAME, filter, null)
        db.close()
    }

}
