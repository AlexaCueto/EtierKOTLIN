import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.etierkotlin.R
import com.example.etierkotlin.model.SpinnerItem

class ItemSpinnerAdapter(
    private val context: Context,
    private val items: List<SpinnerItem>
) : ArrayAdapter<SpinnerItem>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item_rental, parent, false)
        val item = items[position]
        val imageView = view.findViewById<ImageView>(R.id.itemImage)
        val textView = view.findViewById<TextView>(R.id.itemName)
        imageView.setImageResource(item.imageResId)
        textView.text = item.name
        return view
    }
}
