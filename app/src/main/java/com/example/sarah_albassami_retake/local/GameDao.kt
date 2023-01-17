package com.example.sarah_albassami_retake.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sarah_albassami_retake.models.GameItem


@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGame(game: GameItem)

    @Query("SELECT * FROM favorite")
    fun getGames(): LiveData<List<GameItem>>

    @Update
    suspend fun updateGame(game: GameItem)

    @Delete
    suspend fun deleteGame(game: GameItem)
}