package com.ksw.gomovie.model.response

import com.google.gson.annotations.SerializedName
import com.ksw.gomovie.model.main.Movie

/**
 * Created by KSW on 2021-03-04
 */

data class RecommendResponse(
    val page: Int,
    @SerializedName("results")
    val movieList: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
//    val results: List<Movie>
)


