package com.ksw.gomovie.model.detail

import com.google.gson.annotations.SerializedName

/**
 * Created by KSW on 2021-03-12
 */

data class PeopleExternalDetail(
    @SerializedName("facebook_id")
    val facebookId: Any,
    @SerializedName("freebase_id")
    val freebaseId: Any,
    @SerializedName("freebase_mid")
    val freebaseMid: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("instagram_id")
    val instagramId: String,
    @SerializedName("tvrage_id")
    val tvrageId: Any,
    @SerializedName("twitter_id")
    val twitterId: String

)






