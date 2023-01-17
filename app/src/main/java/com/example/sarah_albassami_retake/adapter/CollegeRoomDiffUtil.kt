package com.example.sarah_albassami_retake.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.sarah_albassami_retake.models.GameItem

class CollegeRoomDiffUtil: DiffUtil.ItemCallback<GameItem> () {
    override fun areItemsTheSame(
        oldItem: GameItem,
        newItem: GameItem
    ): Boolean {
        return oldItem==newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: GameItem,
        newItem: GameItem
    ): Boolean {
        return oldItem==newItem
    }

}
