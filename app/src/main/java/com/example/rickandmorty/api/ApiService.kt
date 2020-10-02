package com.example.rickandmorty.api

import com.example.rickandmorty.pojo.Result
import io.reactivex.Observable
import retrofit2.http.GET
import java.util.*

interface ApiService {

    @GET("character/")
    fun getAllInfo() : Observable<Result>
}