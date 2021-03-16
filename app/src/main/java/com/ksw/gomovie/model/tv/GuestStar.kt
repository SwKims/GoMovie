package com.ksw.gomovie.model.tv

import com.google.gson.annotations.SerializedName

/**
 * Created by KSW on 2021-03-16
 */

data class GuestStar(
    val character: String,
    @SerializedName("credit_id")
    val creditId: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val order: Int,
    @SerializedName("profile_path")
    val profilePath: String
)
