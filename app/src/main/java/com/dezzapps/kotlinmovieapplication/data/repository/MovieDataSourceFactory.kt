package com.dezzapps.kotlinmovieapplication.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dezzapps.kotlinmovieapplication.data.api.TheMovieDBInterface
import com.dezzapps.kotlinmovieapplication.data.network.MovieDataSource
import com.dezzapps.kotlinmovieapplication.models.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(private val apiService: TheMovieDBInterface, private val compositeDisposable: CompositeDisposable)
    :DataSource.Factory<Int, Movie>(){

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {

        val movieDataSource = MovieDataSource(apiService, compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)

        return movieDataSource

    }

}