package com.dezzapps.kotlinmovieapplication.data.api

import android.util.Log
import com.dezzapps.kotlinmovieapplication.constants.Constants.Companion.API_KEY
import com.dezzapps.kotlinmovieapplication.constants.Constants.Companion.BASE_URL
import com.dezzapps.kotlinmovieapplication.constants.Constants.Companion.POSTER_BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit




object TheMovieDBClient {
    fun getClient(): TheMovieDBInterface {
        val requestInterceptor = Interceptor {
            chain ->

            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            println(request.url().uri().toString())
            return@Interceptor chain.proceed(request)

        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()


        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDBInterface::class.java)

    }
}