package com.example.etierkotlin.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.etierkotlin.R
import com.example.etierkotlin.model.Rental

class RentalAdapter(
    private val rentalList: List<Rental>,
    private val context: Context,
    private val actionType: String, //"update" or "delete"
    private val onActionClick: (Rental) -> Unit
) : RecyclerView.Adapter<RentalAdapter.RentalViewHolder>() {

    class RentalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textRenterId: TextView = itemView.findViewById(R.id.textRenterId)
        val textItemName: TextView = itemView.findViewById(R.id.textItemName)
        val imageApparel: ImageView = itemView.findViewById(R.id.imageApparel)
        val textCategory: TextView = itemView.findViewById(R.id.textCategory)
        val textRenterName: TextView = itemView.findViewById(R.id.textRenterName)
        val textRentalDates: TextView = itemView.findViewById(R.id.textRentalDates)
        val textStatus: TextView = itemView.findViewById(R.id.textStatus)
        val textNotes: TextView = itemView.findViewById(R.id.textNotes)
        val btnAction: Button = itemView.findViewById(R.id.btnUpdateOrDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_rental, parent, false)
        return RentalViewHolder(view)
    }

    override fun onBindViewHolder(holder: RentalViewHolder, position: Int){
        val rental = rentalList[position]
        holder.textRenterId.text = "ID: ${rental.renterId}"
        holder.textItemName.text = rental.itemName
        holder.textCategory.text = "Category: ${rental.category}"
        holder.textRenterName.text = "Renter: ${rental.renterFirstName} ${rental.renterLastName}"
        holder.textRentalDates.text = "Rental: ${rental.rentalDate} | Return: ${rental.returnDate}"
        holder.textStatus.text = "Status: ${rental.status}"

        val cleanImageName = rental.imageName
            .replace(".jpg", "")
            .replace(".png", "")
            .replace(".webp", "")

        val imageResIdFromCleaned = context.resources.getIdentifier(cleanImageName, "drawable", context.packageName)

        if (imageResIdFromCleaned != 0) {
            holder.imageApparel.setImageResource(imageResIdFromCleaned)
        } else {
            holder.imageApparel.setImageResource(R.drawable.etier_logo_transp)
        }
        Log.d("RentalAdapter", "Image name: ${rental.imageName}, Cleaned Name: $cleanImageName, ResID: $imageResIdFromCleaned")

        holder.btnAction.text = when (actionType) {
            "update" -> "Update"
            "delete" -> "Delete"
            else -> "Action"
        }

        holder.btnAction.setOnClickListener {
            onActionClick(rental)
        }
    }

    override fun getItemCount() = rentalList.size
}



