package com.ksw.gomovie.model.tv

import com.google.gson.annotations.SerializedName

/**
 * Created by KSW on 2021-03-17
 */

data class TvSeasonDetails(
    @SerializedName("air_date")
    val airDate: String,
    val episodes: List<Episode>,
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("season_number")
    val seasonNumber: Int
)
