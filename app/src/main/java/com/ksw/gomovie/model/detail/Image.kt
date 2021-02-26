package com.ksw.gomovie.model.detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by KSW on 2021-02-26
 */
data class Image(
    @SerializedName("file_path")
    @Expose
    val filePath: String,
    @Expose
    @SerializedName("vote_average")
    val rating: Double
)
