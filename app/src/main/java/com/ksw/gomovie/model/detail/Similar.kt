package com.ksw.gomovie.model.detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by KSW on 2021-03-03
 */

data class Similar(
    @Expose val id: Int,
    @Expose val title: String,
    @Expose val name: String,
    @Expose @SerializedName("poster_path") val posterPath: String,
    @Expose @SerializedName("vote_average") val rating: Float
)
