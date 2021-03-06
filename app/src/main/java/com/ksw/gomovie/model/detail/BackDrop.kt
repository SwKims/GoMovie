package com.ksw.gomovie.model.detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by KSW on 2021-02-24
 */

data class BackDrop(
    @SerializedName("aspect_ratio")
    val aspectRatio: Double,
    @SerializedName("file_path")
    @Expose
    val filePath: String,
    val height: Int,
    @SerializedName("iso_639_1")
    val iso6391: Any,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double,
    val rating: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    val width: Int
)
