import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinsulina.R
import com.example.appinsulina.domain.Food
import com.example.appinsulina.ui.FoodFragment
import com.example.appinsulina.ui.MainActivity

class FoodAdapter(
  private val foods: List<Food>,
  private val listener: FoodFragment
) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

  interface OnItemClickListener {
    fun onItemClick(food: Food)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
    return ViewHolder(view)
  }

  override fun getItemCount(): Int = foods.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.txtName.text = foods[position].name
    holder.textProportion.text = foods[position].proportion
    holder.textCalories.text = foods[position].calories
    holder.textCarbohydrate.text = foods[position].carbohydrate
    val food = foods[position]
    holder.bind(food, listener)
  }

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val txtName: TextView = view.findViewById(R.id.txt_name)
    val textProportion: TextView = view.findViewById(R.id.txt_value_proportion)
    val textCalories: TextView = view.findViewById(R.id.txt_value_calories)
    val textCarbohydrate: TextView = view.findViewById(R.id.txt_value_carbohydrate)
    private val cardView: CardView = itemView.findViewById(R.id.card_view)

    fun bind(food: Food, listener: FoodFragment) {
      cardView.setOnClickListener {
        listener.onItemClick(food)
      }
    }
  }
}
