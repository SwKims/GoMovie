package com.ksw.gomovie.model.detail

import com.google.gson.annotations.SerializedName

/**
 * Created by KSW on 2021-03-05
 */

data class PeopleDetail(
    val adult: Boolean,
    @SerializedName("also_known_as")
    val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: String,
    val deathday: Any,
    val gender: Int,
    val homepage: Any,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerializedName("place_of_birth")
    val placeOfBirth: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String,

    @SerializedName("facebook_id")
    val facebookId: String,
    @SerializedName("freebase_id")
    val freebaseId: String,
    @SerializedName("freebase_mid")
    val freebaseMid: String,
    @SerializedName("instagram_id")
    var instagramId: String,
    @SerializedName("tvrage_id")
    val tvrageId: String,
    @SerializedName("twitter_id")
    val twitterId: String

)
