package com.example.recipesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MealAdapter(private var meals: List<Meal>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(meal: Meal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.bind(meal)
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    fun setData(meals: List<Meal>) {
        this.meals = meals
        notifyDataSetChanged()
    }

    fun clearData() {
        meals = emptyList()
        notifyDataSetChanged()
    }

    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val mealImageView: ImageView = itemView.findViewById(R.id.meal_thumbnail)
        private val mealNameTextView: TextView = itemView.findViewById(R.id.meal_name)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(meal: Meal) {
            Picasso.get().load(meal.strMealThumb).into(mealImageView)
            mealNameTextView.text = meal.strMeal
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val meal = meals[position]
                listener.onItemClick(meal)
            }
        }
    }
}
