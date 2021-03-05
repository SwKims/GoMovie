package com.ksw.gomovie.model.main

import com.google.gson.annotations.SerializedName

/**
 * Created by KSW on 2021-03-05
 */

data class People(
    val id: Int,
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String
)
