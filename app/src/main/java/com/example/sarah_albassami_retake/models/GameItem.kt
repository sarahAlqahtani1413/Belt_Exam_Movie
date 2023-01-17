package com.example.sarah_albassami_retake.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite")
data class GameItem(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val genre: String?,
    val platform: String?,
    val short_description: String?,
    val thumbnail: String?,
    val title: String?
)