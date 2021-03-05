package com.ksw.gomovie.repository.recommend

import androidx.lifecycle.LiveData
import com.ksw.gomovie.model.response.MovieResponse
import com.ksw.gomovie.model.response.RecommendResponse
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-03-04
 */

class MovieRecommendRepository(private val apiServiceApi: MovieServiceApi) {

    lateinit var movieMovieRecommendDataSource: MovieRecommendDataSource

    fun loadingMovieRecommends(
        compositeDisposable: CompositeDisposable,
        type: String
    ): LiveData<MovieResponse> {

        movieMovieRecommendDataSource = MovieRecommendDataSource(
            apiServiceApi, compositeDisposable
        )

        return movieMovieRecommendDataSource.movieRecommendResponse
    }

    fun getMovieRecommendNetworkState(): LiveData<NetworkState> {
        return movieMovieRecommendDataSource.networkState
    }

}

