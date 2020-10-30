package com.example.rickandmorty.api

import com.example.rickandmorty.pojo.ExampleResponse
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character/")
    fun getAllInfo(
        @Query("page") page : Int = 1
    ) : Observable<ExampleResponse>

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
        fun getService(): ApiService {
             val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}