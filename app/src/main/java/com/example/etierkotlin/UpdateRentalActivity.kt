package com.example.etierkotlin

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.etier.database.RentalDbHelper
import com.example.etierkotlin.adapter.RentalAdapter
import com.example.etierkotlin.model.Rental
import java.text.SimpleDateFormat
import java.util.*

class UpdateRentalActivity : AppCompatActivity() {

    private lateinit var recyclerViewRentals: RecyclerView
    private lateinit var dbHelper: RentalDbHelper
    private lateinit var adapter: RentalAdapter
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_rentals)

        recyclerViewRentals = findViewById(R.id.recyclerViewRentals)
        recyclerViewRentals.layoutManager = LinearLayoutManager(this)

        dbHelper = RentalDbHelper(this)

        if (dbHelper.getAllRentals().isEmpty()) {
            Toast.makeText(this, "No rentals to update", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        refreshRentalList()
    }

    private fun refreshRentalList() {
        val rentals = dbHelper.getAllRentals()
        adapter = RentalAdapter(rentals, this, "update") { rental ->
            showFullEditDialog(rental)
        }
        recyclerViewRentals.adapter = adapter
    }

    private fun showFullEditDialog(rental: Rental) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_update_options, null)

        val spinnerCategory = dialogView.findViewById<Spinner>(R.id.spinnerCategory)
        val spinnerItemName = dialogView.findViewById<Spinner>(R.id.spinnerItemName)
        val editRenterFName = dialogView.findViewById<EditText>(R.id.editRenterFName)
        val editRenterLName = dialogView.findViewById<EditText>(R.id.editRenterLName)
        val editRentalDate = dialogView.findViewById<EditText>(R.id.editRentalDate)
        val editReturnDate = dialogView.findViewById<EditText>(R.id.editReturnDate)
        val editPrice = dialogView.findViewById<EditText>(R.id.editPrice)
        val editAddress = dialogView.findViewById<EditText>(R.id.editAddress)
        val spinnerStatus = dialogView.findViewById<Spinner>(R.id.spinnerStatus)
        val editNotes = dialogView.findViewById<EditText>(R.id.editNotes)
        val buttonSave = dialogView.findViewById<Button>(R.id.buttonSaveEdit)

        val categories = arrayOf("Maxi Dress", "Formal Gown")
        spinnerCategory.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        spinnerCategory.setSelection(categories.indexOf(rental.category))

        val items = dbHelper.getItemNames(spinnerCategory.selectedItem.toString())
        spinnerItemName.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        spinnerItemName.setSelection(items.indexOf(rental.itemName).takeIf { it >= 0 } ?: 0)

        editRenterFName.setText(rental.renterFirstName)
        editRenterLName.setText(rental.renterLastName)
        editRentalDate.setText(rental.rentalDate)
        editReturnDate.setText(rental.returnDate)
        editPrice.setText(rental.price.toString())
        editAddress.setText(rental.renterAddress)
        editNotes.setText(rental.notes)

        val statuses = arrayOf("Rented", "Returned")
        spinnerStatus.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statuses)
        spinnerStatus.setSelection(statuses.indexOf(rental.status))

        val showDatePicker = { editText: EditText ->
            val cal = Calendar.getInstance()
            val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                cal.set(year, month, day)
                editText.setText(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time))
            }
            DatePickerDialog(
                this,
                listener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        editRentalDate.setOnClickListener { showDatePicker(editRentalDate) }
        editReturnDate.setOnClickListener { showDatePicker(editReturnDate) }

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        buttonSave.setOnClickListener {
            try {
                val updatedRental = rental.copy(
                    category = spinnerCategory.selectedItem.toString(),
                    itemName = spinnerItemName.selectedItem.toString(),
                    renterFirstName = editRenterFName.text.toString().trim(),
                    renterLastName = editRenterLName.text.toString().trim(),
                    rentalDate = editRentalDate.text.toString().trim(),
                    returnDate = editReturnDate.text.toString().trim(),
                    price = editPrice.text.toString().toDoubleOrNull() ?: 0.0,
                    renterAddress = editAddress.text.toString().trim(),
                    status = spinnerStatus.selectedItem.toString(),
                    notes = editNotes.text.toString().trim()
                )

                updateRental(updatedRental)
                dialog.dismiss()
            } catch (e: Exception) {
                Toast.makeText(this, "Failed to save changes: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        dialog.show()
    }

    private fun updateRental(updatedRental: Rental) {
        try {
            if (dbHelper.updateRental(updatedRental)) {
                Toast.makeText(
                    this,
                    "${updatedRental.itemName} updated successfully",
                    Toast.LENGTH_SHORT
                ).show()
                refreshRentalList()
            } else {
                Toast.makeText(
                    this,
                    "Failed to update ${updatedRental.itemName}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Error updating rental: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}