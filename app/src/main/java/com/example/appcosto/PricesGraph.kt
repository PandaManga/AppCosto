package com.example.appcosto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.time.format.DateTimeFormatter
import java.time.temporal.Temporal

private lateinit var sqLiteHelperHistory: SQLiteHelperHistory

class PricesGraph : AppCompatActivity() {

    lateinit var lineGraphView: GraphView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prices_graph)
        sqLiteHelperHistory = SQLiteHelperHistory(this)
        val NumDataPoints = sqLiteHelperHistory.getIDcount(productChosen)
        val Fechas = sqLiteHelperHistory.getStoredDates(productChosen)
        val Precios = sqLiteHelperHistory.getPrices(productChosen)
        val regresarButton = findViewById<ImageButton>(R.id.regresar_button)
        regresarButton.setOnClickListener {
            val Intent = Intent(this, ProductOpen::class.java)
            startActivity(Intent)
        }

        val NewXData = convertFechasAX(Fechas, NumDataPoints)

        lineGraphView = findViewById(R.id.idGraphView)
        val series: LineGraphSeries<DataPoint> = LineGraphSeries()
        for (i in 0 until NumDataPoints) {
            val x = NewXData[i].toDouble()
            val y = Precios[i].toDouble()
            series.appendData(DataPoint(x, y), true, NumDataPoints)
        }
        val MinX = NewXData.min().toDouble()
        val MaxX = NewXData.max().toDouble()
        val MinY = Precios.min().toDouble()
        val MaxY = Precios.max().toDouble()
        lineGraphView.animate()
        lineGraphView.viewport.setMinX(MinX)
        lineGraphView.viewport.setMaxX(MaxX)
        lineGraphView.viewport.setMinY(MinY)
        lineGraphView.viewport.setMaxY(MaxY)
        lineGraphView.viewport.setYAxisBoundsManual(true)
        lineGraphView.viewport.setXAxisBoundsManual(true)
        lineGraphView.viewport.isScrollable = true
        lineGraphView.viewport.isScalable = true
        lineGraphView.viewport.setScalableY(true)
        lineGraphView.viewport.setScrollableY(true)
        series.color = R.color.boton_borrar_color
        lineGraphView.addSeries(series)
    }

    fun convertFechasAX(Fechas: MutableList<String>, NumDataPoints: Int): IntArray {
        var fechaConsecutiva = 0
        var dias  = 0
        var NewFechas  = IntArray(NumDataPoints)
        NewFechas[0] = 0
        var contador = 0
        var NextDate : Temporal
        var FirstDate = parseDate(Fechas[contador])
        if (Fechas.size > 1 ) {
            while (contador < NumDataPoints - 1) {
                var MaxDias = NewFechas.max()
                contador++
                fechaConsecutiva++
                NextDate = parseDate(Fechas[contador])
                dias = ChronoUnit.DAYS.between(FirstDate, NextDate).toInt()
                if (dias == 0) {
                    NewFechas[contador] = MaxDias + 1
                }
                else {
                    NewFechas[contador] = MaxDias + dias
                }
            }
        }
        return NewFechas

    }

    fun parseDate(dateString: String): LocalDate {
        val pattern = "yyyy-MM-dd"
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return LocalDate.parse(dateString, formatter)
    }
}

