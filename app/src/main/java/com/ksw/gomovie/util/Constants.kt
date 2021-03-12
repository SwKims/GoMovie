package com.ksw.gomovie.util

/**
 * Created by KSW on 2021-02-23
 */

class Constants {
    companion object {
        const val BASE_URL: String = "https://api.themoviedb.org/3/"
        const val API_KEY: String = "4defd070082d28cda9bb902140bcf8d0"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
        const val FIRST_PAGE = 1
        const val END_PAGE = 7
        const val LANGUAGE = "ko-KR"
        const val TRAILER_THUMBNAIL_BASE_URL = "https://img.youtube.com/vi/"
        const val TRAILER_THUMBNAIL_END_URL = "/0.jpg"
        const val MOVIE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
        var homePage = ""
        const val MOVIE_TYPE = "movie"
        const val DATABASE_NAME = "movie_database"
        const val MOVIE_ID = "MOVIE_ID"
        const val SHARED_PREF_NAME = "MOVIE_CATALOGUE_SP"
        const val FAVORITE_MOVIE = "favorite_movie"
        var instagram = ""
    }
}