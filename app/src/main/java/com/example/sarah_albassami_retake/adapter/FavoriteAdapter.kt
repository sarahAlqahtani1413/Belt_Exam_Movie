package com.example.sarah_albassami_retake.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sarah_albassami_retake.R
import com.example.sarah_albassami_retake.databinding.FavoriteItemLayoutBinding
import com.example.sarah_albassami_retake.models.GameItem

class FavoriteAdapter(private val clickListener: ClickListener)
    : ListAdapter<GameItem, FavoriteAdapter.ItemViewHolder>(CollegeRoomDiffUtil()) {
    class ItemViewHolder(val binding: FavoriteItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(FavoriteItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val game =  getItem(position)

        if (game.title == "w")
        holder.binding.cvFavoriteItem.visibility = View.GONE

        holder.binding.apply {

            tvFavoriteTitle.text = game.title
            tvFavoritePlatform.text = game.platform
            if (game.thumbnail != "null")
            Glide.with(holder.itemView.context).load(game.thumbnail!!).into(ivShow)
            else
                Glide.with(holder.itemView.context).load(R.drawable.ic_baseline_error_24).into(ivShow)

            btnRemove.setOnClickListener {
                clickListener.deleteFromRoom(game)
                Toast.makeText(holder.itemView.context,"deleted Success", Toast.LENGTH_SHORT).show()
            }

            cvFavoriteItem.setOnClickListener {
                clickListener.onCardClick(game)
               }
        }
    }

    interface ClickListener
    {
        fun deleteFromRoom(game:GameItem)
        fun onCardClick(game:GameItem)
    }
}