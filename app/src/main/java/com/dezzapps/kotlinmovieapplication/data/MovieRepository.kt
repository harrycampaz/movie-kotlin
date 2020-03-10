package com.dezzapps.kotlinmovieapplication.data

import androidx.lifecycle.LiveData
import com.dezzapps.kotlinmovieapplication.data.api.TheMovieDBInterface
import com.dezzapps.kotlinmovieapplication.data.network.MovieDetailsNetworkDataSource
import com.dezzapps.kotlinmovieapplication.models.MoviesDetails
import io.reactivex.disposables.CompositeDisposable

class MovieRepository (private val apiService: TheMovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MoviesDetails>{

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)
        return movieDetailsNetworkDataSource.downloadedMovieDetailsResponse

    }

    fun getMovieDetailsNetWork(): LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState
    }
}