package com.ksw.gomovie.model.response

import com.google.gson.annotations.SerializedName
import com.ksw.gomovie.model.detail.Image

/**
 * Created by KSW on 2021-02-26
 */
data class ImageResponse(
    @SerializedName("backdrops")
    val backdrops: List<Image>
)
