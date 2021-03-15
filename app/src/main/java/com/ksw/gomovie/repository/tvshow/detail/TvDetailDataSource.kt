package com.ksw.gomovie.repository.tvshow.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ksw.gomovie.model.detail.TvDetail
import com.ksw.gomovie.model.response.CastResponse
import com.ksw.gomovie.model.response.VideoResponse
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

/**
 * Created by KSW on 2021-03-15
 */

class TvDetailDataSource(
    private val apiServiceApi: MovieServiceApi,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _tvDetailsResponse = MutableLiveData<TvDetail>()
    val tvDetailsResponse: LiveData<TvDetail>
        get() = _tvDetailsResponse

    private val _tvVideosResponse = MutableLiveData<VideoResponse>()
    val tvVideosResponse: LiveData<VideoResponse>
        get() = _tvVideosResponse

    private val _tvCastResponse = MutableLiveData<CastResponse>()
    val tvCastResponse: LiveData<CastResponse>
        get() = _tvCastResponse

    fun loadTvDetails(tvId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            apiServiceApi.getTVDetails(tvId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _tvDetailsResponse.postValue(it)
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

    fun loadTvVideos(tvId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            apiServiceApi.getTvVideos(tvId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _tvVideosResponse.postValue(it)
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

    fun loadTvCastDetails(tvId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            apiServiceApi.getTvCast(tvId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _tvCastResponse.postValue(it)
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