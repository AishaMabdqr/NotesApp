package com.example.notesapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class RVAdapter (val activity: MainActivity, val itemList : ArrayList<Notes>) : RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val items = itemList[position]

        holder.itemView.apply {
            tvItems.text = items.message

            bEdit.setOnClickListener {
                activity.dialog(items.id)
            }

            bDel.setOnClickListener {
                var selectedItems = items.id
                activity.deleteData(selectedItems)
                Log.d("RVAdapter ", "id equal $selectedItems")
            }
        }
    }

    override fun getItemCount() = itemList.size

}