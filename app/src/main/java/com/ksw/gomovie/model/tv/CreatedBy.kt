package com.ksw.gomovie.model.tv

import com.google.gson.annotations.SerializedName

/**
 * Created by KSW on 2021-03-15
 */

data class CreatedBy(
    @SerializedName("credit_id")
    val creditId: String,
    val gender: Int,
    val id: Int,
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String
)