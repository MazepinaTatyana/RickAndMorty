package com.example.rickandmorty.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Info (

    @SerializedName("count")
    @Expose
    val count: Int = 0,

    @SerializedName("pages")
    @Expose
    val pages: Int = 0,

    @SerializedName("next")
    @Expose
    val next: String? = null,

    @SerializedName("prev")
    @Expose
    val prev: Any? = null
)