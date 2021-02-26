package com.ksw.gomovie.model.response

import com.google.gson.annotations.SerializedName
import com.ksw.gomovie.model.detail.CastDetail
import com.ksw.gomovie.model.detail.CrewDetail

/**
 * Created by KSW on 2021-02-26
 */

data class CastResponse(
    @SerializedName("cast")
    val castDetail: ArrayList<CastDetail>,
    @SerializedName("crew")
    val crewDetail: ArrayList<CrewDetail>,
    val id: Int
)
