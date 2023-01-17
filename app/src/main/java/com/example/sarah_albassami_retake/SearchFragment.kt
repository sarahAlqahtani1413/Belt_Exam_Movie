package com.example.sarah_albassami_retake

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sarah_albassami_retake.adapter.SearchAdapter
import com.example.sarah_albassami_retake.models.GameItem
import com.example.sarah_albassami_retake.models.Games
import com.example.sarah_albassami_retake.remote.APIClient
import com.example.sarah_albassami_retake.remote.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment(), SearchAdapter.ClickListenerSearch {

    private lateinit var mainViewModel: MainViewModel

    private var apiInterface: APIInterface?=null
    private lateinit var rvMain: RecyclerView
    private lateinit var psButton: Button
    private lateinit var browserButton: Button
    private lateinit var addButton: Button
    private lateinit var titleTV: TextView
    private lateinit var platformTV: TextView
    private lateinit var genreTV: TextView
     private lateinit var rvAdapter: SearchAdapter
    private lateinit var games: ArrayList<GameItem>
    private var progressDialog:ProgressDialog?=null
    private var chosenGame: GameItem?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        games = arrayListOf()
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        progressDialog = ProgressDialog(requireActivity())
        progressDialog!!.setTitle("loading")
        progressDialog!!.setCancelable(false)

        platformTV = view.findViewById(R.id.tv_platform)
        genreTV = view.findViewById(R.id.tv_genre)
        titleTV = view.findViewById(R.id.tv_title)
        addButton = view.findViewById(R.id.btn_add_to_favorite)
        psButton = view.findViewById(R.id.btn_search_pc)
        browserButton = view.findViewById(R.id.btn_search_browser)
        rvMain = view.findViewById(R.id.rv_search)
        rvAdapter = SearchAdapter(this@SearchFragment,games,mainViewModel,requireContext())
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(requireActivity())

        apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        psButton.setOnClickListener {
            progressDialog!!.show()
            getData("pc")
        }
        browserButton.setOnClickListener {
            progressDialog!!.show()
            getData("browser")
        }
        addButton.setOnClickListener {
            if (platformTV.text != "" && chosenGame != null) {
                mainViewModel.addGame(chosenGame!!)
                Toast.makeText(requireContext(),"added Success", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun getData(query: String){
        games.clear()
        // here we use the enqueue callback to make sure that we get the data before we update the Recycler View
        // enqueue gives us async functionality like coroutines, later we will replace this with coroutines
        apiInterface?.getGames(platform = query)?.enqueue(object: Callback<Games> {
            override fun onResponse(call: Call<Games>, response: Response<Games>) {
                // we use a try block to make sure that our app doesn't crash if the data is incomplete
                try{
                    // now we have access to all cars from the JSON file, we will only use the first car in this demo (index value 0)
                    if (response.body() != null)
                        for (item in response.body()!!)
                            if (item.title.toString() != "null")
                                    games.add(GameItem(
                                        id = item.id,
                                        title = item.title.toString(),
                                        platform = item.platform.toString(),
                                        short_description = item.short_description.toString(),
                                        genre = item.genre.toString(),
                                        thumbnail = item.thumbnail.toString()))
                                    rvAdapter.update(games)

                    rvAdapter.notifyDataSetChanged()
                    progressDialog!!.dismiss()

                }catch(e: Exception){
                    Log.e("MAIN", "ISSUE: $e")
                }
            }
            override fun onFailure(call: Call<Games>, t: Throwable) {
                Log.e("MAIN", "Unable to get data")
            }

        })
    }

    override fun onCardClick(game: GameItem) {
        chosenGame = game
        platformTV.text = game.platform.toString()
        titleTV.text = game.title.toString()
        genreTV.text = game.genre.toString()

    }
}