package com.example.etierkotlin

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.etier.database.RentalDbHelper
import com.example.etierkotlin.adapter.RentalAdapter
import com.example.etierkotlin.model.Rental

class DeleteRentalsActivity : AppCompatActivity() {

    private lateinit var recyclerViewRentals: RecyclerView
    private lateinit var dbHelper: RentalDbHelper
    private lateinit var adapter: RentalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_rentals)

        recyclerViewRentals = findViewById(R.id.recyclerViewRentals)
        recyclerViewRentals.layoutManager = LinearLayoutManager(this)

        dbHelper = RentalDbHelper(this)

        // Set action type for adapter
        refreshRentalList()
    }

    private fun refreshRentalList() {
        val rentals = dbHelper.getAllRentals()
        if (rentals.isEmpty()) {
            Toast.makeText(this, "No rentals to delete", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        adapter = RentalAdapter(rentals, this, "delete") { rental ->
            showDeleteConfirmation(rental)
        }
        recyclerViewRentals.adapter = adapter
    }

    private fun showDeleteConfirmation(rental: Rental) {
        // Inflate custom dialog layout
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_delete_confirmation, null)

        // Initialize UI elements
        val title = dialogView.findViewById<TextView>(R.id.textViewDialogTitle)
        val message = dialogView.findViewById<TextView>(R.id.textViewDialogMessage)
        val confirmButton = dialogView.findViewById<Button>(R.id.buttonConfirmDelete)
        val cancelButton = dialogView.findViewById<Button>(R.id.buttonCancelDelete)

        // Customize dialog content
        title.text = "Confirm Delete"
        message.text = "Are you sure you want to delete rental for:\n${rental.itemName}?"

        // Build dialog
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Set button actions
        confirmButton.setOnClickListener {
            try {
                if (dbHelper.deleteRental(rental.renterId)) {
                    Toast.makeText(
                        this,
                        "${rental.itemName} deleted successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    refreshRentalList()
                } else {
                    Toast.makeText(
                        this,
                        "Failed to delete ${rental.itemName}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Error deleting rental: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
            dialog.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}