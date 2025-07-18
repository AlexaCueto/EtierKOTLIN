package com.example.etierkotlin

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.etier.database.RentalDbHelper
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class ReportsActivity : AppCompatActivity() { // Remove the generic type parameter
    private lateinit var textTotalRentals: TextView
    private lateinit var textTotalRevenue: TextView
    private lateinit var textMostRentedCategory: TextView
    private lateinit var pieChart: PieChart // Initialize pieChart as lateinit
    private lateinit var dbHelper: RentalDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)

        textTotalRentals = findViewById(R.id.textTotalRentals)
        textTotalRevenue = findViewById(R.id.textTotalRevenue)
        textMostRentedCategory = findViewById(R.id.textMostRentedCategory)
        pieChart = findViewById<PieChart>(R.id.pieChart) // Cast to PieChart
        val buttonBack = findViewById<Button>(R.id.buttonBack)

        dbHelper = RentalDbHelper(this)

        displayReports()
        setupPieChart()

        buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun displayReports() {
        textTotalRentals.text = "Total Rentals: ${dbHelper.getTotalRentals()}"
        textTotalRevenue.text = "Total Revenue: ₱${"%.2f".format(dbHelper.getTotalRevenue())}"
        textMostRentedCategory.text = "Most Rented Category: ${dbHelper.getMostRentedCategory()}"
    }

    private fun setupPieChart() {
        val categoryData = dbHelper.getCategoryBreakdown()
        val entries = ArrayList<PieEntry>()

        categoryData.forEach { (category, count) ->
            entries.add(PieEntry(count.toFloat(), category))
        }

        val dataSet = PieDataSet(entries, "Category Breakdown")
        dataSet.colors = listOf(
            Color.parseColor("#FFA726"),
            Color.parseColor("#66BB6A"),
            Color.parseColor("#29B6F6"),
            Color.parseColor("#AB47BC"),
            Color.parseColor("#FF7043")
        )
        dataSet.valueTextSize = 14f
        dataSet.valueTextColor = Color.WHITE

        pieChart.data = PieData(dataSet) // Access properties of PieChart correctly
        pieChart.description.isEnabled = false
        pieChart.centerText = "Category Breakdown"
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.animateY(1000)
        pieChart.invalidate()
    }
}
