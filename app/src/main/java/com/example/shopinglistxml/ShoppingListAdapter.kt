package com.example.shopinglistxml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShoppingListAdapter(
    private val items: MutableList<String>,
    private val onDeleteClick: (String) -> Unit,
    private val onEditClick: (String, Int) -> Unit
) : RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping_list, parent, false)
        return ShoppingListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onDeleteClick, onEditClick, position)
    }

    override fun getItemCount(): Int = items.size

    class ShoppingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemName: TextView = itemView.findViewById(R.id.itemName)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        fun bind(
            item: String,
            onDeleteClick: (String) -> Unit,
            onEditClick: (String, Int) -> Unit,
            position: Int
        ) {
            itemName.text = item
            deleteButton.setOnClickListener { onDeleteClick(item) }

            // Слушатель для редактирования
            itemView.setOnClickListener {
                onEditClick(item, position)
            }
        }
    }
}
