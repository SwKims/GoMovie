package com.ksw.gomovie.model.response

import com.google.gson.annotations.SerializedName
import com.ksw.gomovie.model.main.TV

/**
 * Created by KSW on 2021-03-12
 */

data class TvResponse(
    val page: Int,
    @SerializedName("results")
    val tvList: List<TV>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
