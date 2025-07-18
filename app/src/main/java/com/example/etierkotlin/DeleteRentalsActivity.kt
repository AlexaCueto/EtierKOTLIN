package com.example.etierkotlin

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.etier.database.RentalDbHelper
import com.example.etierkotlin.adapter.DeleteRentalListAdapter
import com.example.etierkotlin.model.Rental

class DeleteRentalsActivity : AppCompatActivity() {

    private lateinit var listViewRentals: ListView
    private lateinit var dbHelper: RentalDbHelper
    private lateinit var adapter: DeleteRentalListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_rentals)

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener { finish() }

        listViewRentals = findViewById(R.id.listViewRentals)
        dbHelper = RentalDbHelper(this)

        refreshRentalList()
    }

    private fun refreshRentalList() {
        val rentals = dbHelper.getAllRentals()
        if (rentals.isEmpty()) {
            Toast.makeText(this, "No rentals to delete", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        adapter = DeleteRentalListAdapter(this, rentals) { rental ->
            showDeleteConfirmationDialog(rental)
        }
        listViewRentals.adapter = adapter
    }

    private fun showDeleteConfirmationDialog(rental: Rental) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_delete_confirmation, null)

        val textTitle = dialogView.findViewById<TextView>(R.id.textViewDialogTitle)
        val textMessage = dialogView.findViewById<TextView>(R.id.textViewDialogMessage)
        val buttonConfirm = dialogView.findViewById<Button>(R.id.buttonConfirmDelete)
        val buttonCancel = dialogView.findViewById<Button>(R.id.buttonCancelDelete)

        textTitle.text = "Confirm Deletion"
        textMessage.text = "Are you sure you want to delete ${rental.itemName} rented by ${rental.renterFirstName} ${rental.renterLastName}?"

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        buttonConfirm.setOnClickListener {
            try {
                if (dbHelper.deleteRental(rental.renterId)) {
                    Toast.makeText(this, "${rental.itemName} deleted successfully.", Toast.LENGTH_SHORT).show()
                    refreshRentalList()
                } else {
                    Toast.makeText(this, "Failed to delete ${rental.itemName}.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Error deleting rental: ${e.message}", Toast.LENGTH_LONG).show()
            }
            dialog.dismiss()
        }

        buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
