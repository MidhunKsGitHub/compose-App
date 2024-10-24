package com.midhun.composeapp.RetrofitApi

import com.midhun.composeapp.Api.RestApi
import com.midhun.composeapp.Config.Config
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitHelper {


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Config.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: RestApi by lazy {
        retrofit.create(RestApi::class.java)
    }

}