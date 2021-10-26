package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }

    private fun addNotes(){
        dbhelper.saveData(Notes(0, eInput.text.toString()))
        eInput.text.clear()
        Toast.makeText(applicationContext, "Data Saved Successfully", Toast.LENGTH_SHORT).show()
    }

    private fun retrieveNotes(){
        notesArray = dbhelper.retrieveNotes()
        rvAdapter = RVAdapter(notesArray)
        rvItems.adapter = rvAdapter
        rvItems.layoutManager = LinearLayoutManager(this)
        rvAdapter.notifyDataSetChanged()

    }
}