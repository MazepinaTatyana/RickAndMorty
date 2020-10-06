package com.example.rickandmorty.api

import com.example.rickandmorty.pojo.ExampleResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character/")
    fun getAllInfo(
        @Query("page") page : Int = 1
    ) : Observable<ExampleResponse>
}