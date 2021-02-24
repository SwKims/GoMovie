package com.ksw.gomovie.network

import com.ksw.gomovie.model.MovieResponse
import com.ksw.gomovie.model.main.MovieDetail
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by KSW on 2021-02-23
 */

interface MovieServiceApi {

  /*  @GET("movie/{type}?region=in")
    fun getMovieList(
            @Path("type") type: String,
            @Query("page") page: Int
    ): Single<MovieResponse>*/

    @GET("movie/popular")
    fun getMovieList(@Query("page") page : Int) : Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): Single<MovieDetail>

}