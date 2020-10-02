package com.example.rickandmorty.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "RandM")
data class Result (
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private val id: Int = 0,

    @SerializedName("name")
    @Expose
    private val name: String? = null,

    @SerializedName("status")
    @Expose
    private val status: String? = null,

    @SerializedName("species")
    @Expose
    private val species: String? = null,

    @SerializedName("type")
    @Expose
    private val type: String? = null,

    @SerializedName("gender")
    @Expose
    private val gender: String? = null,

    @SerializedName("image")
    @Expose
    private val image: String? = null,

    @SerializedName("episode")
    @Expose
    private val episode: List<String>? = null,

    @SerializedName("url")
    @Expose
    private val url: String? = null,

    @SerializedName("created")
    @Expose
    private val created: String? = null
)

