package com.ksw.gomovie.model.tv

import com.google.gson.annotations.SerializedName

/**
 * Created by KSW on 2021-03-15
 */

data class Network(
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)
