package com.example.sarah_albassami_retake.local


import androidx.lifecycle.LiveData
import com.example.sarah_albassami_retake.models.GameItem

class Repository(private val gameDao: GameDao) {
    val getGames:LiveData<List<GameItem>> = gameDao.getGames()



    suspend fun addGame(game: GameItem){
        gameDao.addGame(game)
    }


    suspend fun deleteGame(game: GameItem){
        gameDao.deleteGame(game)
    }
    suspend fun editGame(game: GameItem){
        gameDao.updateGame(game)
    }

}