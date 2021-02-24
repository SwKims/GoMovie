package com.ksw.gomovie.model.detail

import com.google.gson.annotations.SerializedName

/**
 * Created by KSW on 2021-02-24
 */

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String,
    val name: String
)
