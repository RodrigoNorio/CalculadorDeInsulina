import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinsulina.R
import com.example.appinsulina.domain.Food
import com.example.appinsulina.ui.FoodFragment
import com.squareup.picasso.Picasso



class FoodAdapter(
  private val foods: List<Food>
) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

  var onItemClickListener: (Food) -> Unit = {}

  var onStarClickListener: (Food) -> Unit = {}

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
    return ViewHolder(view)
  }

  override fun getItemCount(): Int = foods.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.loadFoodImg(foods[position])
    holder.txtName.text = foods[position].name
    holder.textProportion.text = foods[position].proportion
    holder.textCalories.text = foods[position].calories
    holder.textCarbohydrate.text = foods[position].carbohydrate
    holder.cardView.setOnClickListener {
      onItemClickListener(foods[position])
    }
    holder.imgStar.setOnClickListener {
      onStarClickListener(foods[position])
      holder.setupFavorite(foods[position], holder)
    }
  }

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val txtName: TextView = view.findViewById(R.id.txt_name)
    val textProportion: TextView = view.findViewById(R.id.txt_value_proportion)
    val textCalories: TextView = view.findViewById(R.id.txt_value_calories)
    val textCarbohydrate: TextView = view.findViewById(R.id.txt_value_carbohydrate)
    val imgFood: ImageView = view.findViewById(R.id.img_food)
    val imgStar: ImageView = view.findViewById(R.id.img_star_favorite)

    val cardView: CardView = itemView.findViewById(R.id.card_view)

    fun loadFoodImg(food: Food) {
      Picasso.get().load(food.urlImg).into(imgFood)
    }

    fun setupFavorite(food: Food, holder: ViewHolder) {
      food.isFavorite = !food.isFavorite
      if(food.isFavorite) {
        holder.imgStar.setImageResource(R.drawable.ic_star_selected)
      }
      else {
        holder.imgStar.setImageResource(R.drawable.ic_star)
      }
    }
  }
}
