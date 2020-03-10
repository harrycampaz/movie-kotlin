package com.dezzapps.kotlinmovieapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dezzapps.kotlinmovieapplication.data.NetworkState
import com.dezzapps.kotlinmovieapplication.models.Movie
import com.dezzapps.kotlinmovieapplication.ui.popular_movie.MoviePagedListRepository
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val moviePagedListRepository: MoviePagedListRepository): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  moviePagedList : LiveData<PagedList<Movie>> by lazy {
        moviePagedListRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val  networkState : LiveData<NetworkState> by lazy {
        moviePagedListRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}