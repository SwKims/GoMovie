package com.ksw.gomovie.model.tv

import com.google.gson.annotations.SerializedName

/**
 * Created by KSW on 2021-03-15
 */

data class Season(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode_count")
    val episodeCount: Int,
    val _id: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("season_number")
    val seasonNumber: Int
)
