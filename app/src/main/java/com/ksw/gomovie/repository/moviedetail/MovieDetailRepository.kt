package com.ksw.gomovie.repository.moviedetail

import androidx.lifecycle.LiveData
import com.ksw.gomovie.model.detail.PeopleDetail
import com.ksw.gomovie.model.response.VideoResponse
import com.ksw.gomovie.model.main.MovieDetail
import com.ksw.gomovie.model.response.CastResponse
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-02-24
 */

class MovieDetailRepository(private val apiServiceApi: MovieServiceApi) {

    lateinit var movieDetailDataSource: MovieDetailDataSource

    fun loadingMovieDetails(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetail> {
        movieDetailDataSource =
            MovieDetailDataSource(
                apiServiceApi,
                compositeDisposable
            )
        movieDetailDataSource.loadMovieDetails(movieId)

        return movieDetailDataSource.movieDetailsResponse
    }

    fun loadingMovieVideos(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<VideoResponse> {
        movieDetailDataSource =
            MovieDetailDataSource(
                apiServiceApi,
                compositeDisposable
            )
        movieDetailDataSource.loadMovieVideos(movieId)

        return movieDetailDataSource.movieVideoResponse
    }

    fun loadingCastDetails(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<CastResponse> {
        movieDetailDataSource =
            MovieDetailDataSource(
                apiServiceApi,
                compositeDisposable
            )
        movieDetailDataSource.loadCastDetails(movieId)

        return movieDetailDataSource.movieCastResponse
    }

    fun getMovieDetailNetworkState(): LiveData<NetworkState> {
        return movieDetailDataSource.networkState
    }

}