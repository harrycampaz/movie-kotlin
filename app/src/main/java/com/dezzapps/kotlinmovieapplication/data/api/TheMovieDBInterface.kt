package com.dezzapps.kotlinmovieapplication.data.api

import com.dezzapps.kotlinmovieapplication.models.MoviesDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDBInterface {

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MoviesDetails>
}