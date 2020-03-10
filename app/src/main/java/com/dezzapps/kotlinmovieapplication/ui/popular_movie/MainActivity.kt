package com.dezzapps.kotlinmovieapplication.ui.popular_movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.dezzapps.kotlinmovieapplication.R
import com.dezzapps.kotlinmovieapplication.data.NetworkState
import com.dezzapps.kotlinmovieapplication.data.api.TheMovieDBClient
import com.dezzapps.kotlinmovieapplication.data.api.TheMovieDBInterface
import com.dezzapps.kotlinmovieapplication.ui.details_movie.DetailsMoviesActivity
import com.dezzapps.kotlinmovieapplication.viewmodel.DetailsMovieViewModel
import com.dezzapps.kotlinmovieapplication.viewmodel.MainActivityViewModel
import io.reactivex.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private  lateinit var viewModel: MainActivityViewModel
    lateinit var moviePagedListRepository: MoviePagedListRepository



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()

        moviePagedListRepository = MoviePagedListRepository(apiService)

        viewModel = getViewModel()

        val movieAdapter = PopularMoviePagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                return if (viewType == movieAdapter.MOVIE_VIEW_TYPE) 1    // Movie_VIEW_TYPE will occupy 1 out of 3 span
                else 3                                              // NETWORK_VIEW_TYPE will occupy all 3 span
            }
        }

        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        viewModel.moviePagedList.observe(this, androidx.lifecycle.Observer {
            movieAdapter.submitList(it)
        })


        viewModel.networkState.observe(this, androidx.lifecycle.Observer {
            progress_bar_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            text_error_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })




    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {

                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(moviePagedListRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}
