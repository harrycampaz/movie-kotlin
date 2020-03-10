package com.dezzapps.kotlinmovieapplication

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.dezzapps.kotlinmovieapplication.constants.Constants.Companion.POSTER_BASE_URL
import com.dezzapps.kotlinmovieapplication.data.MovieRepository
import com.dezzapps.kotlinmovieapplication.data.NetworkState
import com.dezzapps.kotlinmovieapplication.data.api.TheMovieDBClient
import com.dezzapps.kotlinmovieapplication.data.api.TheMovieDBInterface
import com.dezzapps.kotlinmovieapplication.models.MoviesDetails
import com.dezzapps.kotlinmovieapplication.viewmodel.DetailsMovieViewModel
import kotlinx.android.synthetic.main.activity_details_movies.*
import java.text.NumberFormat
import java.util.*


class DetailsMoviesActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailsMovieViewModel
    private lateinit var movieRepository: MovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_movies)

        if (Build.VERSION.SDK_INT > 9) {
            val policy =
                StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        var movieId: Int = intent.getIntExtra("id", 475303)

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {

            bindUI(it)

        } )

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            text_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })


    }

    private fun bindUI(it: MoviesDetails) {

        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.voteAverage.toString()
        movie_runtime.text = it.runtime.toString() + "Minutes"
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster);

    }

    private fun getViewModel(movieId: Int): DetailsMovieViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {

                @Suppress("UNCHECKED_CAST")
                return DetailsMovieViewModel(movieRepository, movieId) as T
            }
        })[DetailsMovieViewModel::class.java]
    }
}
