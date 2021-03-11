package com.ksw.gomovie.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.ksw.gomovie.model.Result
import com.ksw.gomovie.util.Constants.Companion.FAVORITE_MOVIE

/**
 * Created by KSW on 2021-03-11
 */

@Entity(tableName = FAVORITE_MOVIE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    @SerializedName("vote_average")
    val rating: Double,
)

