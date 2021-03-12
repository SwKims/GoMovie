package com.ksw.gomovie.model.main

import com.google.gson.annotations.SerializedName

/**
 * Created by KSW on 2021-03-12
 */

data class TV(
    val id: Int,
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double
)
