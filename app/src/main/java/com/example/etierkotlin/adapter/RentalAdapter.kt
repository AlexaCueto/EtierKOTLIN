package com.example.etierkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.etierkotlin.R
import com.example.etierkotlin.model.Rental
import com.example.etierkotlin.utils.Utils

class RentalAdapter(
    private val rentalList: List<Rental>,
    private val context: Context,
    private val actionType: String, // "update" or "delete"
    private val onActionClick: (Rental) -> Unit
) : BaseAdapter() {

    private class ViewHolder(itemView: View) {
        val textRenterId: TextView = itemView.findViewById(R.id.textRenterId)
        val textItemName: TextView = itemView.findViewById(R.id.textItemName)
        val imageApparel: ImageView = itemView.findViewById(R.id.imageApparel)
        val textCategory: TextView = itemView.findViewById(R.id.textCategory)
        val textRenterName: TextView = itemView.findViewById(R.id.textRenterName)
        val textRentalDates: TextView = itemView.findViewById(R.id.textRentalDates)
        val textStatus: TextView = itemView.findViewById(R.id.textStatus)
        val textPrice: TextView = itemView.findViewById(R.id.textPrice)
        val textNotes: TextView = itemView.findViewById(R.id.textNotes)
        val btnAction: Button = itemView.findViewById(R.id.btnUpdateOrDelete)
    }

    override fun getCount(): Int = rentalList.size

    override fun getItem(position: Int): Any = rentalList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.recycler_item_rental, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val rental = getItem(position) as Rental
        holder.textRenterId.text = "ID: ${rental.renterId}"
        holder.textItemName.text = rental.itemName
        holder.textCategory.text = "Category: ${rental.category}"
        holder.textRenterName.text = "Renter: ${rental.renterFirstName} ${rental.renterLastName}"
        holder.textRentalDates.text = "Rental: ${rental.rentalDate} | Return: ${rental.returnDate}"
        holder.textStatus.text = "Status: ${rental.status}"
        holder.textPrice.text = "Price: ₱${rental.price}"
        holder.textNotes.text = "Notes: ${rental.notes}"

        val imageName = Utils.getImageNameForItem(rental.itemName)
        val imageResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

        if (imageResId != 0) {
            holder.imageApparel.setImageResource(imageResId)
        } else {
            holder.imageApparel.setImageResource(R.drawable.etier_logo_transp)
        }

        //Action button logic
        if (actionType == "view") {
            holder.btnAction.visibility = View.GONE
        } else {
            holder.btnAction.visibility = View.VISIBLE
            holder.btnAction.text = when (actionType) {
                "update" -> "Update"
                "delete" -> "Delete"
                else -> "Action"
            }
            holder.btnAction.setOnClickListener {
                onActionClick(rental)
            }
        }

        return view
    }
}


