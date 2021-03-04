package com.ksw.gomovie.model.main

import com.google.gson.annotations.SerializedName
import com.ksw.gomovie.model.detail.ProductionCompany
import com.ksw.gomovie.model.detail.ProductionCountry
import com.ksw.gomovie.model.detail.SpokenLanguage
import com.ksw.gomovie.model.response.ImageResponse

/**
 * Created by KSW on 2021-02-24
 */

data class MovieDetail(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    /*@SerializedName("belongs_to_collection")
    val belongsToCollection: CollectionResponse,*/
    val budget: String,
    val genres: ArrayList<Genre>,
    val homepage: String,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: String,
    val runtime: Int,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    var images: ImageResponse? = null
)