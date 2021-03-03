package com.ksw.gomovie.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ksw.gomovie.model.detail.Similar

/**
 * Created by KSW on 2021-03-03
 */

data class SimilarResponse(
    @Expose @SerializedName("page") val page: Int,
    @Expose @SerializedName("results") val similar: List<Similar>,
    @Expose @SerializedName("total_pages") val totalPages: Int,
    @Expose @SerializedName("total_results") val totalResults: Int
)
