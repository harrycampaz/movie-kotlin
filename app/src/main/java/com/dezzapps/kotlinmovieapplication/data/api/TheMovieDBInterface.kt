package com.dezzapps.kotlinmovieapplication.data.api

import com.dezzapps.kotlinmovieapplication.models.MovieResponse
import com.dezzapps.kotlinmovieapplication.models.MoviesDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

    //https://api.themoviedb.org/3/movie/popular?api_key=<<api_key>>&language=en-US&page=1

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MoviesDetails>
}