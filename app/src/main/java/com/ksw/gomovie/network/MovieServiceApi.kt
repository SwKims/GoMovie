package com.ksw.gomovie.network

import com.ksw.gomovie.model.response.MovieResponse
import com.ksw.gomovie.model.response.VideoResponse
import com.ksw.gomovie.model.main.MovieDetail
import com.ksw.gomovie.model.response.CastResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by KSW on 2021-02-23
 */

interface MovieServiceApi {

    @GET("movie/{type}?region=kr")
    fun getMovieList(
            @Path("type") type: String,
            @Query("page") page: Int
    ): Single<MovieResponse>

   /* @GET("movie/popular")
    fun getMovieList(@Query("page") page : Int) : Single<MovieResponse>*/

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): Single<MovieDetail>

    @GET("movie/{id}/videos")
    fun getMovieVideos(
        @Path("id") movieId: Int
    ): Single<VideoResponse>

    @GET("movie/{id}/credits")
    fun getMovieCast(
        @Path("id") movieId: Int
    ): Single<CastResponse>

    @GET("search/movie")
    fun getSearchResults(
        @Query("query") query: String
    ): Single<MovieResponse>

}