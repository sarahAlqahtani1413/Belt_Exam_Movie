package com.example.sarah_albassami_retake

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sarah_albassami_retake.local.GameDao
import com.example.sarah_albassami_retake.local.GameDatabase
import com.example.sarah_albassami_retake.local.Repository
import com.example.sarah_albassami_retake.models.GameItem
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class MainViewModel(application: Application): AndroidViewModel(application) {
//    private val showDao by lazy { ShowDatabase.getDatabase(application).showDao() }
//    private val shows: MutableLiveData<List<ShowX>> = MutableLiveData()

    private val repository: Repository
    private val games: LiveData<List<GameItem>>
    private val gameDao: GameDao

    init {
        gameDao= GameDatabase.getDatabase(application).gameDao()
        repository=Repository(gameDao)
        games=repository.getGames
    }

    fun addGame(game: GameItem) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addGame(game)
        }
    }

    fun getGame(): LiveData<List<GameItem>> {
        return games
    }


    fun deleteGame(game: GameItem) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteGame(game)
        }
    }
    fun editCollege(game: GameItem) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.editGame(game)
        }
    }

}

