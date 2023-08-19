package com.example.myapplication

import android.database.sqlite.SQLiteDatabase
import com.example.rubberbridge.Contract
import com.example.rubberbridge.Game
import com.example.rubberbridge.Robber
import java.text.SimpleDateFormat

class Database constructor(_db: SQLiteDatabase?)  {

    val db: SQLiteDatabase? = _db
    val dateFormatter = SimpleDateFormat("dd.MM.yyyy")

    fun createTables (){

        //db?.execSQL("DROP TABLE games;")
        db?.execSQL("CREATE TABLE IF NOT EXISTS games (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                            "level INTEGER, "   +
                                                            "suit INTEGER, "    +
                                                            "result INTEGER,"   +
                                                            "team INTEGER,"     +
                                                            "dbl INTEGER)")
       db?.execSQL("DELETE FROM games;")


         db?.execSQL("CREATE TABLE IF NOT EXISTS teams (teamID INTEGER UNIQUE, " +
               "Player1 TEXT, "   +
              "Player2 TEXT)")


        db?.execSQL("DELETE FROM teams;")
    }

     fun insertData(level: Int,suit: Int,result: Int,player: Int ,double: Int ) {

      val sqlstring = "INSERT INTO games (level, suit, result, team, dbl) VALUES ("+level+","+suit+","+result+","+player+","+double+");"

      db?.execSQL(sqlstring)
    }


      fun insertTeam(teamID: Int,Player1: String,Player2: String) {

      val sqlstring = "INSERT INTO teams (teamID, Player1, Player2) VALUES ("+teamID+",'"+Player1+"','"+Player2+"');"

       db?.execSQL(sqlstring)
      }


     fun getTeams() :MutableList<String>{
       val query = db?.rawQuery("SELECT * FROM teams;", null)

        var teamsArray: MutableList<String> = mutableListOf()

      while (query?.moveToNext()==true) {
        val teamID: Int = query.getInt(0)
        val Player1: String = query.getString(1)
        val Player2: String = query.getString(2)


      teamsArray.add(Player1+"/"+Player2)

     }

      return teamsArray
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