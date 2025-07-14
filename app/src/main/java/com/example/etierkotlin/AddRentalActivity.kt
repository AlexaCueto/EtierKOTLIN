package com.example.etierkotlin

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.etier.database.RentalDbHelper
import com.example.etierkotlin.model.Rental
import java.util.*

class AddRentalActivity : AppCompatActivity() {
    private lateinit var editItemName: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var editRenterFName: EditText
    private lateinit var editRenterLName: EditText
    private lateinit var editRentalDate: EditText
    private lateinit var editReturnDate: EditText
    private lateinit var editPrice: EditText
    private lateinit var spinnerStatus: Spinner
    private lateinit var editNotes: EditText
    private lateinit var buttonSaveRental: Button

    private lateinit var dbHelper: RentalDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_rental)

        dbHelper = RentalDbHelper(this)

        editItemName = findViewById(R.id.editItemName)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        editRenterFName = findViewById(R.id.editRenterFName)
        editRenterLName = findViewById(R.id.editRenterLName)
        editRentalDate = findViewById(R.id.editRentalDate)
        editReturnDate = findViewById(R.id.editReturnDate)
        editPrice = findViewById(R.id.editPrice)
        spinnerStatus = findViewById(R.id.spinnerStatus)
        editNotes = findViewById(R.id.editNotes)
        buttonSaveRental = findViewById(R.id.buttonSaveRental)

        //spinners
        setupSpinners()

        //date pickers
        setupDatePicker(editRentalDate)
        setupDatePicker(editReturnDate)

        //button click
        buttonSaveRental.setOnClickListener {
            saveRentalRecord()
        }
    }

    private fun setupSpinners(){
        val categories = listOf("Maxi Dresses", "Formal Gowns")
        val statusOptions = listOf("Rented", "Returned")

        spinnerCategory.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        spinnerStatus.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statusOptions)
    }

    @SuppressLint("DefaultLocale")
    private fun setupDatePicker(editText: EditText) {
        editText.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this, { _, y, m, d ->
                val formattedDate = String.format("%04d-%02d-%02d", y, m + 1, d)
                editText.setText(formattedDate)
            }, year, month, day)
            dpd.show()
        }
    }

    private fun saveRentalRecord() {
        val itemName = editItemName.text.toString().trim()
        val selectedCategory = spinnerCategory.selectedItem.toString()
        val categoryEnum = ApparelCategory.fromDisplayName(selectedCategory)

        if (categoryEnum == null) {
            Toast.makeText(this, "Invalid Category Selected!", Toast.LENGTH_SHORT).show()
            return
        }

        val renterFirstName = editRenterFName.text.toString().trim()
        val renterLastName = editRenterLName.text.toString().trim()
        val rentalDate = editRentalDate.text.toString().trim()
        val returnDate = editReturnDate.text.toString().trim()
        val price = editPrice.text.toString().toDoubleOrNull()
        val status = spinnerStatus.selectedItem.toString()
        val notes = editNotes.text.toString().trim()

        if (
            itemName.isNotEmpty() && renterFirstName.isNotEmpty() && renterLastName.isNotEmpty()
            && rentalDate.isNotEmpty() && returnDate.isNotEmpty() && price != null
        ) {
            val renterId = UUID.randomUUID().toString().take(8)

            val rental = Rental(
                renterId = renterId,
                itemName = itemName,
                category = categoryEnum.displayName,
                renterFirstName = renterFirstName,
                renterLastName = renterLastName,
                rentalDate = rentalDate,
                returnDate = returnDate,
                price = price,
                status = status,
                notes = notes
            )

            val success = dbHelper.addRental(rental)

            if (success) {
                Toast.makeText(this, "Rental record added!", Toast.LENGTH_SHORT).show()
                clearFields()
            } else {
                Toast.makeText(this, "Error adding record.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please fill out all required fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields(){
        editItemName.text.clear()
        editRenterFName.text.clear()
        editRenterLName.text.clear()
        editRentalDate.text.clear()
        editReturnDate.text.clear()
        editPrice.text.clear()
        editNotes.text.clear()
        spinnerCategory.setSelection(0)
        spinnerStatus.setSelection(0)
    }
}
