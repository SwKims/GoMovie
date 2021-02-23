package com.ksw.gomovie.model.main

import com.google.gson.annotations.SerializedName

/**
 * Created by KSW on 2021-02-23
 */

data class Movie(
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double
)
