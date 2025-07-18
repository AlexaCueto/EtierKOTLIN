package com.example.etierkotlin

import android.content.Intent
import com.example.etierkotlin.adapter.RentalAdapter
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.etier.database.RentalDbHelper

class ViewRentalsActivity : AppCompatActivity() {

    private lateinit var listViewRentals: ListView
    private lateinit var dbHelper: RentalDbHelper
    private lateinit var adapter: RentalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_rentals)
        val buttonBack = findViewById<Button>(R.id.buttonBack)

        listViewRentals = findViewById(R.id.listViewRentals)

        dbHelper = RentalDbHelper(this)
        refreshRentalList()

        buttonBack.setOnClickListener{
            finish()
        }
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
            listViewRentals.adapter = adapter

    }
}

