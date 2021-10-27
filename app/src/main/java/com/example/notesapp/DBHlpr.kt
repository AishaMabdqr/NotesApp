package com.example.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHlpr (context: Context) : SQLiteOpenHelper(context, "details.db", null, 3) {

    var sqLiteDatabase : SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL("create table notes (pk INTEGER PRIMARY KEY AUTOINCREMENT , Message text)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        // I dropped the previous table because I added the primary key so to keep the data consistent
        db!!.execSQL("DROP TABLE IF EXISTS notes")
        onCreate(db)
    }

    fun saveData(note : Notes){
        val cv = ContentValues()
        cv.put("Message",note.message)
        sqLiteDatabase.insert("notes" , null, cv)
    }

    fun retrieveNotes() : ArrayList<Notes>{

        var notes = ArrayList<Notes>()

        //Read All data using cursor
        var c : Cursor = sqLiteDatabase.rawQuery("SELECT * FROM notes",null)

        c.moveToFirst()

        if (c.count < 1){ // Handle empty table
            println("No data found")

            }else{
                do{ //Iterate through the table
                    val id = c.getInt(0) //  the number refers to the num of the column
                    val msg = c.getString(c.getColumnIndex("Message"))
                    notes.add(Notes(id,msg))
                }while (c.moveToNext())
        }
        return notes
    }

    fun updateData(pk : Int, Message: String) {
        val cv = ContentValues()
        cv.put("Message", Message)
        sqLiteDatabase.update("notes", cv, "pk = $pk", null)
    }

    fun deleteData(pk : Int){
        sqLiteDatabase.delete("notes", "pk = $pk", null)
        Log.d("DBHlpr", "pk $pk")
    }
}