package com.example.rickandmorty.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.rickandmorty.converters.Converter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "RandM")
@TypeConverters(value = [Converter::class])
data class Result (
    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("status")
    @Expose
    val status: String,

    @SerializedName("species")
    @Expose
    val species: String,

    @SerializedName("type")
    @Expose
    val type: String,

    @SerializedName("gender")
    @Expose
    val gender: String,

    @SerializedName("image")
    @Expose
    val image: String,

    @SerializedName("episode")
    @Expose
    val episode: List<String>,

    @SerializedName("url")
    @Expose
    val url: String,

    @SerializedName("created")
    @Expose
    val created: String
)

