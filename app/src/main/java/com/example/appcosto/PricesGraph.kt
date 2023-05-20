package com.example.appcosto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        val NewXData = convertFechasAX(Fechas, NumDataPoints-1)

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

    fun convertFechasAX(Fechas: MutableList<String>, NumDataPoints: Int): Array<Int> {
        var NewFechas : Array<Int> = arrayOf(NumDataPoints)
        NewFechas[0] = 1
        var contador = 0
        var NextDate : Temporal
        var FirstDate = parseDate(Fechas[contador])
        if (Fechas.size > 1 ) {
            while (contador < NumDataPoints - 1) {
                contador++
                NextDate = parseDate(Fechas[contador])
                NewFechas[contador] = ChronoUnit.DAYS.between(FirstDate, NextDate).toInt()
                if (NewFechas[contador] == 0) {
                    NewFechas[contador] = 1
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

