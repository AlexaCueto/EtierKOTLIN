package com.example.itemmanagement

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttonAddRental: Button
    private lateinit var buttonViewRentals: Button
    private lateinit var buttonReports: Button
    private lateinit var dbHelper: RentalDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = RentalDb(this)

        buttonAddRental = findViewById (R.id.buttonAddRental)
        buttonViewRentals = findViewById (R.id.buttonViewRentals)
        buttonReports = findViewById (R.id.buttonReports)

        buttonAddRental.setOnClickListener {
            val intent = Intent(this, AddRentalActivity::class.java)
            startActivity(intent)
        }

        buttonViewRentals.setOnClickListener {
            val intent = Intent(this, ViewRentalsActivity::class.java)
            startActivity(intent)
        }

        buttonReports.setOnClickListener {
            val intent = Intent(this, ReportsActivity::class.java)
            startActivity(intent)
        }
    }

}