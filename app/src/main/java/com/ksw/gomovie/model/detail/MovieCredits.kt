package com.ksw.gomovie.model.detail

import com.ksw.gomovie.model.main.Movie

/**
 * Created by KSW on 2021-03-08
 */

data class MovieCredits(
    val cast: List<Movie>,
    val id: Int
)
