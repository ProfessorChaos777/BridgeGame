package com.example.myapplication

import android.R.string
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.rubberbridge.Contract
import com.example.rubberbridge.Game
import com.example.rubberbridge.Robber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Database constructor(_db: SQLiteDatabase?)  {

    val db: SQLiteDatabase? = _db
    val dateFormatter = SimpleDateFormat("dd.MM.yyyy")

    fun createTables (){

        //db?.execSQL("DROP TABLE games;")
        db?.execSQL("CREATE TABLE IF NOT EXISTS games (id INTEGER PRIMARY KEY AUTOINCREMENT, level INTEGER, suit INTEGER, result INTEGER, team INTEGER, dbl INTEGER)")
        db?.execSQL("DELETE FROM games;")
    }

    fun insertData(a: Int,b: Int,c: Int,d: Int ,e: Int ) {



        val sqlstring = "INSERT INTO games (level,suit,result,team,dbl) VALUES ("+a+","+b+","+c+","+d+","+e+");"

        db?.execSQL(sqlstring)
    }


    fun getRobber() :Robber{



        val query = db?.rawQuery("SELECT * FROM games;", null)

        var robber: Robber = Robber()

        while (query?.moveToNext()==true) {

            val level: Int = query.getInt(1)
            val suit: Int = query.getInt(2)
            val result: Int = query.getInt(3)
            val team: Int = query.getInt(4)
            val dbl: Int = query.getInt(5)

            robber.addGame(Game(team, result, Contract(level, suit,dbl)))

        }





        return robber
    }



    fun deleteLastRecord() {



        val sqlstring = "DELETE FROM games WHERE id = (SELECT MAX(id) FROM games);"

        db?.execSQL(sqlstring)
    }








}