package com.example.sarah_albassami_retake

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sarah_albassami_retake.adapter.FavoriteAdapter
import com.example.sarah_albassami_retake.databinding.FragmentFavoriteBinding
import com.example.sarah_albassami_retake.models.GameItem
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() , FavoriteAdapter.ClickListener{
    private lateinit var binding: FragmentFavoriteBinding

    private lateinit var mainViewModel: MainViewModel

    private lateinit var rvAdapter: FavoriteAdapter
    lateinit var gamesList:List<GameItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentFavoriteBinding.inflate(layoutInflater)

        gamesList = listOf()

        rvAdapter = FavoriteAdapter(this)

        binding.apply {
            rvFavorite.adapter = rvAdapter
            rvFavorite.layoutManager = LinearLayoutManager(requireContext())
            fabSearch.setOnClickListener {
                Navigation.findNavController(binding.root).navigate(R.id.action_favoriteFragment_to_searchFragment)
            }

        }
        //view model
//        mainViewModel = ViewModelProvider(this@FavoriteFragment)[MainViewModel::class.java]
//        mainViewModel.getShow().observe(requireActivity()) {
//            rvAdapter.submitList(it)
//        }
        //view model
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        lifecycleScope.launch {
            mainViewModel.getGame().observe(requireActivity()) {
                rvAdapter.submitList(it)
            }
        }


        return binding.root
    }

    override fun deleteFromRoom(game: GameItem) {
        alertDialogToDelete(game)
        Toast.makeText(requireContext(),"the game was deleted ", Toast.LENGTH_LONG).show()
    }

    override fun onCardClick(game: GameItem) {
        val intent = activity?.intent
        intent?.putExtra("title", game.title)
        intent?.putExtra("platform", game.platform)
        intent?.putExtra("genre", game.genre)
        intent?.putExtra("short_description", game.short_description)
        intent?.putExtra("image", game.thumbnail)

        NavHostFragment.findNavController(this@FavoriteFragment).navigate(R.id.action_favoriteFragment_to_detailFragment)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun alertDialogToDelete(game: GameItem) {

        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setMessage("Sure you want delete!!")
                .setPositiveButton("Ok") { _, _ ->
                    run {
                        mainViewModel.deleteGame(game)
                        rvAdapter.submitList(gamesList)
                        Toast.makeText(requireContext(), "the game was deleted ", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

        val alert = dialogBuilder.create()
            alert.setTitle("Delete Game:")
            alert.show()

    }
}

