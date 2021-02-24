package com.ksw.gomovie.model.detail

import com.google.gson.annotations.SerializedName

/**
 * Created by KSW on 2021-02-24
 */

data class Poster(
    @SerializedName("aspect_ratio")
    val aspectRatio: Double,
    @SerializedName("file_path")
    val filePath: String,
    val height: Int,
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    val width: Int
)
