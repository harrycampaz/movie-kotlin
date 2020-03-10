package com.dezzapps.kotlinmovieapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dezzapps.kotlinmovieapplication.data.MovieRepository
import com.dezzapps.kotlinmovieapplication.data.NetworkState
import com.dezzapps.kotlinmovieapplication.models.MoviesDetails
import io.reactivex.disposables.CompositeDisposable

class DetailsMovieViewModel(private val moviesRepository: MovieRepository, movieId: Int): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails: LiveData<MoviesDetails> by lazy {
        moviesRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        moviesRepository.getMovieDetailsNetWork()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}