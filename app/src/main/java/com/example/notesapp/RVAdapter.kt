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



class RVAdapter (val activity: MainActivity, var itemList : ArrayList<Notes>) : RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {

    private val dbhelper by lazy { DBHlpr(activity) }

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

                val dialogBuilder = AlertDialog.Builder(activity)
                val updatedNote = EditText(activity)
                updatedNote.hint = "Enter new note"

                dialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Save", DialogInterface.OnClickListener {
                            _, _ -> dbhelper.updateData(items.id, updatedNote.text.toString())
                        itemList.clear()
                        itemList.addAll(dbhelper.retrieveNotes())
                        update(itemList)

                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                            dialog, _ -> dialog.cancel()
                    })
                val alert = dialogBuilder.create()
                alert.setTitle("Update Note")
                alert.setView(updatedNote)
                alert.show()
            }

            bDel.setOnClickListener {
                var selectedItems = items.id
                activity.deleteData(selectedItems)
                Log.d("RVAdapter ", "id equal $selectedItems")
                itemList.clear()
                itemList.addAll(dbhelper.retrieveNotes())
                update(itemList)
            }
        }
    }

    override fun getItemCount() = itemList.size

    fun update(itemList : ArrayList<Notes>){
        this.itemList = itemList
        notifyDataSetChanged()
    }

}