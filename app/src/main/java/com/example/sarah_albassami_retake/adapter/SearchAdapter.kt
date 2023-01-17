package com.example.sarah_albassami_retake.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sarah_albassami_retake.R
import com.example.sarah_albassami_retake.models.GameItem
import com.example.sarah_albassami_retake.MainViewModel
import com.example.sarah_albassami_retake.databinding.SearchItemLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchAdapter(private val clickListener: ClickListenerSearch,private var names: ArrayList<GameItem>, private var mainViewModel: MainViewModel, private var context: Context): RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: SearchItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val game =  names[position]

        holder.binding.apply {
            tvSearchName.text = game.title

            cvSearchItem.setOnClickListener {
                clickListener.onCardClick(game)
            }

            ivSearchFavorite.setOnClickListener {
                if (game.thumbnail != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        mainViewModel.addGame(GameItem(
                            id = game.id!!,
                            title = game.title.toString(),
                            thumbnail = game.thumbnail.toString(),
                            short_description = game.short_description.toString(),
                             platform = game.platform.toString(),
                            genre = game.genre.toString()
                        )
                        )
                    }
                    ivSearchFavorite.setImageResource(R.drawable.ic_favorite)

                    Log.e("Tag",game.id.toString())
                }else {
                    mainViewModel.addGame(GameItem(
                        id = game.id!!,
                        title = game.title.toString(),
                        thumbnail = game.thumbnail.toString(),
                        short_description = game.short_description.toString(),
                        platform = game.platform.toString(),
                        genre = game.genre.toString()
                    )
                    )
                    Log.e("Tag","w")
                }
                Toast.makeText(context,"added Success",Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun update(names: ArrayList<GameItem>){
        this.names = names
        notifyDataSetChanged()
    }

    interface ClickListenerSearch
    {
        fun onCardClick(game:GameItem)
    }

    override fun getItemCount() = names.size
}