package com.example.sarah_albassami_retake.remote

import com.example.sarah_albassami_retake.models.Games
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("games")
    fun getGames(@Query("platform") platform : String = "pc"): Call<Games>
}