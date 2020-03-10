package com.dezzapps.kotlinmovieapplication.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dezzapps.kotlinmovieapplication.data.NetworkState
import com.dezzapps.kotlinmovieapplication.data.api.TheMovieDBInterface
import com.dezzapps.kotlinmovieapplication.models.MoviesDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailsNetworkDataSource (private val apiService: TheMovieDBInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState = MutableLiveData<NetworkState>()

    val networkState: LiveData<NetworkState>
        get() = _networkState


    private val _downloadedMovieDetailsResponse = MutableLiveData<MoviesDetails>()

    val downloadedMovieDetailsResponse: LiveData<MoviesDetails>
        get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId: Int){
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        _downloadedMovieDetailsResponse.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)

                    }, {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.d("MovieDetailsDataSource", it.message)
                    })
            )

        }catch (e: Exception){

        }

    }

}