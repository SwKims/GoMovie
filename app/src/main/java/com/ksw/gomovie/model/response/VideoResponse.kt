package com.ksw.gomovie.model.response

import com.google.gson.annotations.SerializedName
import com.ksw.gomovie.model.detail.VideoDetail

/**
 * Created by KSW on 2021-02-25
 */

data class VideoResponse(
    val id: Int,
    @SerializedName("results")
    val videosList: ArrayList<VideoDetail>
)
