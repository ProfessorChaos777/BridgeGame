package com.example.rubberbridge

const val CLUBS = 20
const val DIAMONDS = 20
const val HEARTS = 30
const val SPADES = 30
const val NOTRAMP = 30
const val NOTRAMP_BASE = 40

const val FIRST_GAME_AWARD = 200
const val SECOND_GAME_AWARD = 500

const val SMALL_SLAM = 500
const val SMALL_SLAM_IN_ZONE = 750

const val BIG_SLAM = 1000
const val BIG_SLAM_IN_ZONE = 1500

const val GAME = 100

const val FIRST_TEAM = 1
const val SECOND_TEAM = 2

class Robber(var table: Table_to_draw=Table_to_draw()) {

    var games: MutableList<Game> = mutableListOf()

    fun addGame(game:Game){
        games.add(game)
        updatetable()
    }
    fun removeGame(){
        games.removeAt(games.size - 1)
        updatetable()
    }

    //здесь будет обновляться таблица
    fun  updatetable(){
        table = Table_to_draw()
        var pointResult:PointResult
        for(game in games){
            pointResult=getPointResult(game,table.zoneTeam1,table.zoneTeam2)

            if(pointResult.winnerteam == FIRST_TEAM){
                table.allPointsTeam1 += pointResult.allPoints
                table.partPointsTeam1 += pointResult.partPoints
                if(table.partPointsTeam1 >= GAME){
                    if(table.zoneTeam1){
                        table.endGame = true
                        //геймовая премия
                        table.allPointsTeam1 += SECOND_GAME_AWARD
                    }else{
                        table.partPointsTeam1 = 0
                        table.partPointsTeam2 = 0
                        table.zoneTeam1 = true
                        //геймовая премия
                        table.allPointsTeam1 += FIRST_GAME_AWARD
                    }
                }
            }
            if(pointResult.winnerteam == SECOND_TEAM){
                table.allPointsTeam2 += pointResult.allPoints
                table.partPointsTeam2 += pointResult.partPoints
                if(table.partPointsTeam2 >= GAME){
                    if(table.zoneTeam2){
                        table.endGame = true
                        table.allPointsTeam2 += SECOND_GAME_AWARD
                        //геймовая премия
                    }
                    else{
                        table.partPointsTeam2 = 0
                        table.partPointsTeam1 = 0
                        table.zoneTeam2 = true
                        //геймовая премия
                        table.allPointsTeam2 += FIRST_GAME_AWARD
                    }
                }
            }
        }
    }


    //здесь преобразовывается результат игры в очки
    fun  getPointResult(game:Game,zoneTeam1:Boolean,zoneTeam2:Boolean):PointResult{
        var pointResult:PointResult=PointResult(0,0,0)
        val zoneGame:Boolean = (zoneTeam1 && game.team == 1)||(zoneTeam2 && game.team == 2)
        if(game.contract.dbl == 0){
            if(game.result >= game.contract.level + 6){
                when(game.contract.suit){
                    0-> pointResult.partPoints = game.contract.level * CLUBS
                    1-> pointResult.partPoints = game.contract.level * DIAMONDS
                    2-> pointResult.partPoints = game.contract.level * HEARTS
                    3-> pointResult.partPoints = game.contract.level * SPADES
                    4-> pointResult.partPoints = game.contract.level * NOTRAMP_BASE
                }

                when(game.contract.suit){
                    0-> pointResult.allPoints = (game.result - 6) * CLUBS
                    1-> pointResult.allPoints = (game.result - 6) * DIAMONDS
                    2-> pointResult.allPoints = (game.result - 6) * HEARTS
                    3-> pointResult.allPoints = (game.result - 6) * SPADES
                    4-> pointResult.allPoints = (game.result - 6) * NOTRAMP_BASE
                }

                pointResult.winnerteam=game.team
            }
            else
            {
                pointResult.allPoints=(game.contract.level + 6 - game.result) * 50
                if(zoneGame) pointResult.allPoints *= 2

                when(game.team){
                    1-> pointResult.winnerteam = 2
                    2-> pointResult.winnerteam = 1
                }
            }
        }
        else
        {
            if(game.result >= game.contract.level + 6){
                when(game.contract.suit){
                    0-> pointResult.partPoints = game.contract.level * CLUBS
                    1-> pointResult.partPoints = game.contract.level * DIAMONDS
                    2-> pointResult.partPoints = game.contract.level * HEARTS
                    3-> pointResult.partPoints = game.contract.level * SPADES
                    4-> pointResult.partPoints = game.contract.level * NOTRAMP_BASE
                }
                pointResult.partPoints=pointResult.partPoints*2



                pointResult.allPoints=(game.result - 6-game.contract.level)*100
                if(zoneGame) pointResult.allPoints *= 2
                pointResult.allPoints+=pointResult.partPoints
                pointResult.allPoints+=50



                pointResult.winnerteam=game.team
            }
            else
            {

                if(zoneGame) {
                    when(game.contract.level + 6 - game.result){
                        1-> pointResult.allPoints=200
                        else-> pointResult.allPoints=300*(game.contract.level + 6 - game.result)-100
                    }
                }else{
                    when(game.contract.level + 6 - game.result){
                        1-> pointResult.allPoints=100
                        2-> pointResult.allPoints=300
                        3-> pointResult.allPoints=500
                        else-> pointResult.allPoints=300*(game.contract.level + 6 - game.result)-400
                    }
                }

                when(game.team){
                    1-> pointResult.winnerteam = 2
                    2-> pointResult.winnerteam = 1
                }
            }


            //если реконтра
            if(game.contract.dbl == 2){
                pointResult.allPoints*=2
                pointResult.partPoints*=2
            }


        }

        //шлемики
        if(game.result>=game.contract.level + 6)
        {
            pointResult.allPoints += when(game.contract.level){
                6->if(zoneGame) SMALL_SLAM_IN_ZONE else SMALL_SLAM
                7->if(zoneGame) BIG_SLAM_IN_ZONE else BIG_SLAM
                else->0
            }
        }

        return pointResult
    }

}

class PointResult(var winnerteam:Int,var allPoints:Int,var partPoints:Int)

//result - количество взяток
class Game(var team:Int,var result:Int,var contract:Contract)

class Table_to_draw (var allPointsTeam1:Int = 0,
                     var partPointsTeam1:Int = 0,
                     var zoneTeam1:Boolean = false,
                     var allPointsTeam2:Int = 0,
                     var partPointsTeam2:Int = 0,
                     var zoneTeam2:Boolean = false,
                     var endGame:Boolean = false)

//suit: 0- миноры , 1-мажоры, 2- бк
//dbl 0- без контры, 1- с контрой , 2- с реконтрой
class Contract(val level:Int,val suit:Int,val dbl:Int)