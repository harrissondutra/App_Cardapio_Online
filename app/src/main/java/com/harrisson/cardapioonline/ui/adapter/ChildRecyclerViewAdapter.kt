package com.harrisson.cardapioonline.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harrisson.cardapioonline.R
import com.harrisson.cardapioonline.models.ChildItem

class ChildRecyclerViewAdapter(
    private val context : Context,
    private val childItemList : ArrayList<ChildItem>,
    var clickListener : FoodInterface
) : RecyclerView.Adapter<ChildRecyclerViewAdapter.ChildItemViewHolder>() {

    private var qtd = 0

    inner class ChildItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val childImageView = itemView.findViewById<ImageView>(R.id.img_food)
        val childTitle = itemView.findViewById<TextView>(R.id.txt_name_food)
        val childPrice = itemView.findViewById<TextView>(R.id.txt_register_price_food)
        val childDescription = itemView.findViewById<TextView>(R.id.txt_menu_food_description)
        val childQtd = itemView.findViewById<TextView>(R.id.edt_qtd_food)

        var buttonAdd = itemView.findViewById<ImageView>(R.id.btn_add_food)
        var btn_remove = itemView.findViewById<ImageView>(R.id.btn_rem_food)
        var editText = itemView.findViewById<TextView>(R.id.edt_qtd_food)
        var clearButton = itemView.findViewById<ImageView>(R.id.btn_checkout)

        fun vincula(food : ChildItem) {
            val image = itemView.findViewById<ImageView>(R.id.img_food)
            image.setImageResource(food.image)

            val name = itemView.findViewById<TextView>(R.id.txt_name_food)
            name.text = food.title

            val price = itemView.findViewById<TextView>(R.id.txt_register_price_food)
            price.text = "R$ " + "%.2f".format(food.price)
//            price.text = "R$ " + food.price_food

            val description = itemView.findViewById<TextView>(R.id.txt_menu_food_description)
            description.text = food.description

            val qtd = itemView.findViewById<TextView>(R.id.edt_qtd_food)
            qtd.text = food.qtd.toString()

        }
    }

    override fun onCreateViewHolder(
        parent : ViewGroup, viewType : Int
    ) : ChildItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.child_item, parent, false)
        return ChildItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder : ChildItemViewHolder, position : Int
    ) {
        holder.childImageView.setImageResource(childItemList[position].image)
        holder.childTitle.text = childItemList[position].title
        holder.childPrice.text = childItemList[position].price.toString()
        holder.childQtd.text = childItemList[position].qtd.toString()
        holder.childDescription.text = childItemList[position].description

        val food = childItemList[position]

        holder.childQtd.text = "$qtd"
        holder.childQtd.setTextColor(Color.GRAY)

        //Ao executar addFood
        holder.buttonAdd.setOnClickListener {

            holder.editText.setText("$qtd")
            if (food.qtd == 0) {
                holder.editText.setTextColor(Color.BLACK)
            }
            food.qtd = food.qtd + 1  // Increment qtd directly within the food object
            holder.editText.text = "${food.qtd}"  // Update text with the incremented value
            clickListener.addFood(food)
        }

        //Ao exeutar removeFood
        holder.btn_remove.setOnClickListener {
            if (food.qtd > 0) {  // Ensure qtd doesn't go below zero
                food.qtd = food.qtd - 1

                holder.editText.text = "${food.qtd}"
                clickListener.removeFood(food)  // Call removeFood for consistency
            }
        }

        //Vincular dados ao view holder
        holder.vincula(food)
    }

    override fun getItemCount() : Int {
        return childItemList.size
    }

    interface FoodInterface {
        fun addFood(food : ChildItem)
        fun removeFood(food : ChildItem)
    }
}