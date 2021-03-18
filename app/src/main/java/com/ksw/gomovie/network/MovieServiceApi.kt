package com.ksw.gomovie.network

import com.ksw.gomovie.model.detail.MovieCredits
import com.ksw.gomovie.model.detail.PeopleDetail
import com.ksw.gomovie.model.detail.PeopleExternalDetail
import com.ksw.gomovie.model.detail.TvDetail
import com.ksw.gomovie.model.main.FeatureMovieLists
import com.ksw.gomovie.model.main.MovieDetail
import com.ksw.gomovie.model.main.PeopleImages
import com.ksw.gomovie.model.response.*
import com.ksw.gomovie.model.tv.TvSeasonDetails
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

    @GET("genre/movie/list")
    fun getMovieGenresList(): Single<GenresResponse>

    @GET("movie/{movie_id}/recommendations")
    fun getMovieRecommendations(
        @Path("movie_id") movieId: Int
    ): Single<MovieResponse>

    @GET("person/{id}")
    fun getPeopleDetails(
        @Path("id") id: Int
    ): Single<PeopleDetail>

    @GET("person/{id}/images")
    fun getPeopleImages(
        @Path("id") id: Int
    ): Single<PeopleImages>

    @GET("person/{id}/movie_credits")
    fun getMovieCredits(
        @Path("id") id: Int
    ): Single<MovieCredits>

    @GET("/person/{person_id}/external_ids")
    fun getPeopleSNS(
        @Path("person_id") id: Int
    ): Single<PeopleDetail>

    // tv

    @GET("tv/{type}")
    fun getTVList(
        @Path("type") type: String,
        @Query("page") page: Int
    ): Single<TvResponse>

    @GET("tv/{tv_id}")
    fun getTVDetails(
        @Path("tv_id") tvId: Int
    ): Single<TvDetail>

    @GET("tv/{tv_id}/videos")
    fun getTvVideos(
        @Path("tv_id") tvId: Int
    ): Single<VideoResponse>

    @GET("tv/{tv_id}/credits")
    fun getTvCast(
        @Path("tv_id") tvId: Int
    ): Single<CastResponse>

    @GET("tv/{tv_id}/season/{season_number}")
    fun getTvSeason(
        @Path("tv_id") tvId: Int,
        @Path("season_number") seasonNumber: Int
    ): Single<TvSeasonDetails>

    @GET("tv/{tv_id}/season/{season_number}/videos")
    fun getTvSeasonVideos(
        @Path("tv_id") tvId: Int,
        @Path("season_number") seasonNumber: Int
    ): Single<VideoResponse>

    @GET("tv/{tv_id}/season/{season_number}/credits")
    fun getTvSeasonCast(
        @Path("tv_id") tvId: Int,
        @Path("season_number") seasonNumber: Int
    ) : Single<CastResponse>

    @GET("discover/movie?&region=kr&sort_by=popularity.desc")
    fun getAwardsMovies(
        @Query("with_genres") id: Int,
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET("list/{list_id}")
    fun getFeaturedMovies(
        @Path("list_id") listId: Int
    ): Single<FeatureMovieLists>

}