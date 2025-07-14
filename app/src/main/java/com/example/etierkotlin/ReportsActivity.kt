package com.example.etierkotlin

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.etier.database.RentalDbHelper
import com.example.etierkotlin.model.Rental
import com.example.etierkotlin.R

class ReportsActivity : AppCompatActivity() {
    private lateinit var textTotalRentals: TextView
    private lateinit var textTotalRevenue: TextView
    private lateinit var textMostRentedCategory: TextView

    private lateinit var dbHelper: RentalDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)

        textTotalRentals = findViewById(R.id.textTotalRentals)
        textTotalRevenue = findViewById(R.id.textTotalRevenue)
        textMostRentedCategory = findViewById(R.id.textMostRentedCategory)

        dbHelper = RentalDbHelper(this)

        displayReports()
    }

    private fun displayReports() {
        val totalRentals = dbHelper.getTotalRentals()
        val totalRevenue = dbHelper.getTotalRevenue()
        val mostRentedCategory = dbHelper.getMostRentedCategory()

        textTotalRentals.text = "Total Rentals: $totalRentals"
        textTotalRevenue.text = "Total Revenue: ₱${"%.2f".format(totalRevenue)}"
        textMostRentedCategory.text = "Most Rented Category: $mostRentedCategory"
    }
}

