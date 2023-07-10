package com.example.myapplication

import android.R.string
import android.content.Context
import android.database.sqlite.SQLiteDatabase

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
class Database constructor(_db: SQLiteDatabase?)  {

    val db: SQLiteDatabase? = _db
    val dateFormatter = SimpleDateFormat("dd.MM.yyyy")

    fun createTables (){


        db?.execSQL("CREATE TABLE IF NOT EXISTS games (id INTEGER PRIMARY KEY AUTOINCREMENT, level INTEGER, suit INTEGER, result INTEGER, team INTEGER)")
        db?.execSQL("DELETE FROM games;")
    }

    fun insertData(a: Int,b: Int,c: Int,d: Int ) {



        val sqlstring = "INSERT INTO games (level,suit,result,team) VALUES ("+a+","+b+","+c+","+d+");"

        db?.execSQL(sqlstring)
    }









}