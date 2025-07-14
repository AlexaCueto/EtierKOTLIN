package com.example.etierkotlin

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.etier.database.RentalDbHelper

class ViewRentalsActivity : AppCompatActivity() {

    private lateinit var recyclerViewRentals: ListView
    private lateinit var dbHelper: RentalDbHelper
    private lateinit var adapter: RentalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_rentals)

        recyclerViewRentals = findViewById(R.id.recyclerViewRentals)
        recyclerViewRentals.layoutManager = LinearLayoutManager(this)

        dbHelper = RentalDbHelper(this)
        refreshRentalList()
    }

    private fun refreshRentalList() {
        val rentals = dbHelper.getAllRentals()
        if (rentals.isEmpty()) {
            Toast.makeText(this, "No rental records found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        adapter = RentalAdapter(this, rentals)
        recyclerViewRentals.adapter = adapter
    }
}