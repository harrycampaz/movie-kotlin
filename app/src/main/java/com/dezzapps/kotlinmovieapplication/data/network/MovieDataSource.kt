package com.dezzapps.kotlinmovieapplication.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.dezzapps.kotlinmovieapplication.constants.Constants.Companion.FIRST_PAGE
import com.dezzapps.kotlinmovieapplication.data.NetworkState
import com.dezzapps.kotlinmovieapplication.data.api.TheMovieDBInterface
import com.dezzapps.kotlinmovieapplication.models.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Int, Movie>() {


    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.movies, null, page + 1)
                    networkState.postValue(NetworkState.LOADED)
                },{

                    networkState.postValue(NetworkState.ERROR)
                    Log.e("MoviedataDource", it.message)

                })
        )

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe({
                   if(it.totalPages >= params.key){

                       callback.onResult(it.movies, params.key + 1)
                       networkState.postValue(NetworkState.ENDOFLIST)

                   }else {

                   }
                },{

                    networkState.postValue(NetworkState.ERROR)
                    Log.e("de MovieDataSource", it.message)

                })
        )

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }


}