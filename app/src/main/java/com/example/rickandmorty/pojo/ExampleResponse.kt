package com.example.rickandmorty.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class ExampleResponse {
    @SerializedName("results")
    @Expose
    val results: List<Result>? = null

    @SerializedName("info")
    @Expose
    val info: Info? = null
}