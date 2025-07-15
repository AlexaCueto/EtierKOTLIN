import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.etierkotlin.R
import com.example.etierkotlin.model.Rental

class RentalAdapter(
    private val rentalList: List<Rental>,
    private val context: Context
) : RecyclerView.Adapter<RentalAdapter.RentalViewHolder>() {

    class RentalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textRenterId: TextView = itemView.findViewById(R.id.textRenterId)
        val textItemName: TextView = itemView.findViewById(R.id.textItemName)
        val imageApparel: ImageView = itemView.findViewById(R.id.imageApparel)
        val textCategory: TextView = itemView.findViewById(R.id.textCategory)
        val textRenterName: TextView = itemView.findViewById(R.id.textRenterName)
        val textRentalDates: TextView = itemView.findViewById(R.id.textRentalDates)
        val textStatus: TextView = itemView.findViewById(R.id.textStatus)
        val buttonEditRental: ImageView = itemView.findViewById(R.id.buttonEditRental)
        val buttonDeleteRental: ImageView = itemView.findViewById(R.id.buttonDeleteRental)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_rental, parent, false)
        return RentalViewHolder(view)
    }

    override fun onBindViewHolder(holder:RentalViewHolder, position: Int){
        val rental = rentalList[position]
        holder.textRenterId.text = "ID: ${rental.renterId}"
        holder.textItemName.text = rental.itemName
        holder.textCategory.text = "Category: ${rental.category}"
        holder.textRenterName.text = "Renter: ${rental.renterFirstName} ${rental.renterLastName}"
        holder.textRentalDates.text = "Rental: ${rental.rentalDate} | Return: ${rental.returnDate}"
        holder.textStatus.text = "Status: ${rental.status}"

        val imageResId = context.resources.getIdentifier(rental.imageName, "drawable", context.packageName)
        if (imageResId != 0) {
            holder.imageApparel.setImageResource(imageResId)
        } else {
            holder.imageApparel.setImageResource(R.drawable.etier_logo_transp)
        }

        holder.buttonEditRental.setOnClickListener {
            Toast.makeText(context, "Edit ${rental.itemName}", Toast.LENGTH_SHORT).show()
            //WILL NEED TO IMPLEMENT EDIT LOGIC HERE
        }

        holder.buttonDeleteRental.setOnClickListener {
            Toast.makeText(context, "Delete ${rental.itemName}", Toast.LENGTH_SHORT).show()
            //WILL NEED TO IMPLEMENT EDIT LOGIC HERE
        }
    }

    override fun getItemCount() = rentalList.size
}


