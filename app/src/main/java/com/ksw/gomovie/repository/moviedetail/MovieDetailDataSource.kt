package com.ksw.gomovie.repository.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ksw.gomovie.model.response.VideoResponse
import com.ksw.gomovie.model.main.MovieDetail
import com.ksw.gomovie.model.response.CastResponse
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

/**
 * Created by KSW on 2021-02-24
 */

class MovieDetailDataSource(
    private val apiServiceApi: MovieServiceApi,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _movieDetailsResponse = MutableLiveData<MovieDetail>()
    val movieDetailsResponse: LiveData<MovieDetail>
        get() = _movieDetailsResponse

    private val _movieVideoResponse = MutableLiveData<VideoResponse>()
    val movieVideoResponse: LiveData<VideoResponse>
        get() = _movieVideoResponse

    private val _movieCastResponse = MutableLiveData<CastResponse>()
    val movieCastResponse: LiveData<CastResponse>
        get() = _movieCastResponse

    fun loadMovieDetails(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            apiServiceApi.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _movieDetailsResponse.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                    }
                ).let {
                    compositeDisposable.add(it)
                }
        } catch (e: Exception) {

        }
    }

    fun loadMovieVideos(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            apiServiceApi.getMovieVideos(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _movieVideoResponse.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                    }
                ).let {
                    compositeDisposable.add(it)
                }
        } catch (e: Exception) {

        }
    }

    fun loadCastDetails(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            apiServiceApi.getMovieCast(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _movieCastResponse.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                    }
                ).let {
                    compositeDisposable.add(it)
                }
        } catch (e: Exception) {

        }
    }



}