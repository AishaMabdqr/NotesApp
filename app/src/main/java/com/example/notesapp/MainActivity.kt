package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var rvItems : RecyclerView
    lateinit var eInput : EditText
    lateinit var bAdd :Button

    var msg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvItems = findViewById(R.id.rvItems)
        eInput = findViewById(R.id.eInput)
        bAdd = findViewById(R.id.bAdd)

        bAdd.setOnClickListener {
            msg = eInput.text.toString()

            var dbhr = DBHlpr(applicationContext)
            dbhr.saveData(msg)

            Toast.makeText(applicationContext, "Data Saved Successfully", Toast.LENGTH_SHORT).show()

            eInput.text.clear()
            eInput.clearFocus()

        }
    }
}