package com.ksw.gomovie.model.main

import com.google.gson.annotations.SerializedName

/**
 * Created by KSW on 2021-03-18
 */
data class FeatureMovieLists(
    @SerializedName("created_by")
    val createdBy: String,
    val description: String,
    @SerializedName("favorite_count")
    val favoriteCount: Int,
    val id: String,
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("item_count")
    val itemCount: Int,
    val items: List<Movie>,
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String
)
