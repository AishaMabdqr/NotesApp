package com.example.notesapp

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.LayoutDirection
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var rvItems : RecyclerView
    lateinit var eInput : EditText
    lateinit var bAdd :Button
    lateinit var rvAdapter: RVAdapter
    lateinit var notesArray : ArrayList<Notes>


    private val dbhelper by lazy { DBHlpr(applicationContext) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvItems = findViewById(R.id.rvItems)
        eInput = findViewById(R.id.eInput)
        bAdd = findViewById(R.id.bAdd)
        notesArray = ArrayList()

        bAdd.setOnClickListener {
            addNotes()
             retrieveNotes()
        }

        updateRv()
    }

    fun updateRv(){
        rvAdapter = RVAdapter(this,notesArray)
        rvItems.adapter = rvAdapter
        rvItems.layoutManager = LinearLayoutManager(this)
    }

    private fun addNotes(){
        dbhelper.saveData(Notes(0, eInput.text.toString()))
        eInput.text.clear()
        Toast.makeText(applicationContext, "Data Saved Successfully", Toast.LENGTH_SHORT).show()
    }

    private fun retrieveNotes(){
        notesArray = dbhelper.retrieveNotes()
        updateRv()
    }

    fun updateData(pk: Int, newNote : String){
        if (pk != null) {
            dbhelper.updateData(pk, newNote)
            updateRv()
        }
        else {
            Log.d("Main ", "pk is null")
        }

    }

     fun deleteData(pk : Int){
         if (pk != null) {
             dbhelper.deleteData(pk)
             updateRv()
         } else {
             Log.d("Main ", "pk is null")
         }
    }

    fun dialog(pk : Int){
        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = "Enter new note"

        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                    _, _ -> updateData(pk, updatedNote.text.toString())
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }
}