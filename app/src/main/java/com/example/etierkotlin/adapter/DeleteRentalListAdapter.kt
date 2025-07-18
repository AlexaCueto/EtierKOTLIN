package com.example.etierkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.etierkotlin.R
import com.example.etierkotlin.model.Rental
import com.example.etierkotlin.utils.Utils

class DeleteRentalListAdapter(
    private val context: Context,
    private val rentals: List<Rental>,
    private val onDeleteClick: (Rental) -> Unit
) : BaseAdapter() {

    override fun getCount() = rentals.size

    override fun getItem(position: Int) = rentals[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.recycler_item_rental, parent, false)

        val rental = getItem(position)

        val textRenterId = view.findViewById<TextView>(R.id.textRenterId)
        val imageApparel = view.findViewById<ImageView>(R.id.imageApparel)
        val textItemName = view.findViewById<TextView>(R.id.textItemName)
        val textCategory = view.findViewById<TextView>(R.id.textCategory)
        val textRenterName = view.findViewById<TextView>(R.id.textRenterName)
        val textRentalDates = view.findViewById<TextView>(R.id.textRentalDates)
        val textStatus = view.findViewById<TextView>(R.id.textStatus)
        val textNotes = view.findViewById<TextView>(R.id.textNotes)
        val btnDelete = view.findViewById<Button>(R.id.btnUpdateOrDelete)

        textRenterId.text = "ID: ${rental.renterId}"
        imageApparel.setImageResource(Utils.getImageResForItem(rental.itemName))
        textItemName.text = "Item: ${rental.itemName}"
        textCategory.text = "Category: ${rental.category}"
        textRenterName.text = "Renter: ${rental.renterFirstName} ${rental.renterLastName}"
        textRentalDates.text = "Rental: ${rental.rentalDate} → ${rental.returnDate}"
        textStatus.text = "Status: ${rental.status}"
        textNotes.text = "Notes: ${rental.notes}"

        btnDelete.text = "Delete"
        btnDelete.setOnClickListener { onDeleteClick(rental) }

        return view
    }
}
