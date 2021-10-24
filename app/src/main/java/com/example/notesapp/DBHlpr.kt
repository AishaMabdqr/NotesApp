package com.example.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHlpr (context: Context) : SQLiteOpenHelper(context, "details.db", null, 1) {

    var sqLiteDatabase : SQLiteDatabase = writableDatabase

    override fun onCreate(p0: SQLiteDatabase?) {

        if (p0 != null) {
            p0.execSQL("create table notes (Message text)")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun saveData(msg : String){
        val cv = ContentValues()
        cv.put("Message",msg)
        sqLiteDatabase.insert("notes" , null, cv)
    }
}