package com.ksw.gomovie.model.detail

import com.google.gson.annotations.SerializedName

/**
 * Created by KSW on 2021-02-24
 */

data class SpokenLanguage(
    @SerializedName("iso_639_1")
    val iso6391: String,
    val name: String
)
