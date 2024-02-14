package com.harrisson.cardapioonline.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harrisson.cardapioonline.models.ParentItem
import com.harrisson.cardapioonline.R

class ParentRecyclerViewAdapter(
    private val parentItemList : List<ParentItem>,
    private var clickListener : ChildRecyclerViewAdapter.FoodInterface
) : RecyclerView.Adapter<ParentRecyclerViewAdapter.ParentRecyclerViewHolder>() {

    inner class ParentRecyclerViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

         val parentImageView = itemView.findViewById<ImageView>(R.id.parent_icon)
         val parentTitle = itemView.findViewById<TextView>(R.id.parent_tite)
         val childRecyclerView = itemView.findViewById<RecyclerView>(R.id.child_recyclerview)
         val txtUp = itemView.findViewById<TextView>(R.id.txt_up)

        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)


    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ParentRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.parent_item, parent, false)
        return ParentRecyclerViewHolder(view)
    }

    override fun getItemCount() : Int {
        return parentItemList.size
    }

    override fun onBindViewHolder(holder : ParentRecyclerViewHolder, position : Int) {
        val parentItem = parentItemList[position]

        holder.parentTitle.text = parentItem.title
        holder.parentImageView.setImageResource(parentItem.image)
        holder.txtUp.text = " "

        holder.childRecyclerView.setHasFixedSize(true)
        val recyclerViewMenu = holder.childRecyclerView

        //verificar e alterar caso não fique bom
        var linearLayoutManager = LinearLayoutManager(holder.childRecyclerView.context, LinearLayoutManager.VERTICAL, false)


        val adapter = ChildRecyclerViewAdapter(holder.childRecyclerView.context, parentItem.childItemList, clickListener, )
        holder.childRecyclerView.adapter = adapter

        recyclerViewMenu.layoutManager = linearLayoutManager
        recyclerViewMenu.addItemDecoration(
            DividerItemDecoration(
                holder.childRecyclerView.context,
                linearLayoutManager.orientation
            )
        )

        //função expandable
        val isExpanded = parentItem.isExpanded
        holder.childRecyclerView.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.txtUp.text = if (isExpanded) "Recolher" else " "
        holder.txtUp.setTextColor(if (isExpanded) Color.GRAY else Color.WHITE)

        holder.txtUp.setOnClickListener {
            parentItem.isExpanded = !parentItem.isExpanded
            notifyItemChanged(position)
        }


        holder.constraintLayout.setOnClickListener {

            isAnyItemExpanded(position)

            parentItem.isExpanded = !parentItem.isExpanded
            notifyItemChanged(position)



        }

    }

    private fun isAnyItemExpanded(position : Int) {

        val temp = parentItemList.indexOfFirst { it.isExpanded }

        if(temp >= 0 && temp != position) {
            parentItemList[temp].isExpanded = true
            notifyItemChanged(temp)
        }

    }
}