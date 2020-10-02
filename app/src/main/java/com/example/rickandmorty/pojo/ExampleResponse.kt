package com.example.rickandmorty.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class ExampleResponse {
    @SerializedName("results")
    @Expose
    private val results: List<Result>? = null
}