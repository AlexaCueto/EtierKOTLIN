package com.example.etierkotlin

import android.content.Intent
import com.example.etierkotlin.adapter.RentalAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.etier.database.RentalDbHelper

class ViewRentalsActivity : AppCompatActivity() {

    private lateinit var recyclerViewRentals: RecyclerView
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

       val actionType = intent.getStringExtra("actionType") ?: "view"

        adapter = RentalAdapter(rentals, this, actionType) { rental ->
            when(actionType) {
                "update" -> {
                    val intent = Intent(this, UpdateRentalActivity::class.java)
                    intent.putExtra("rentalId", rental.renterId)
                    startActivity(intent)
                }
                "delete" -> {
                    val intent = Intent(this, DeleteRentalsActivity::class.java)
                    intent.putExtra("rentalId", rental.renterId)
                    startActivity(intent)
                }
                else -> {
                    Toast.makeText(this, "Invalid action type", Toast.LENGTH_SHORT).show()
                }
            }
        }
            recyclerViewRentals.adapter = adapter

    }
}

