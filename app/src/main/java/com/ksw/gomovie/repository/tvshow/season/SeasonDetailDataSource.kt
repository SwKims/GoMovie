package com.ksw.gomovie.repository.tvshow.season

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ksw.gomovie.model.response.CastResponse
import com.ksw.gomovie.model.response.VideoResponse
import com.ksw.gomovie.model.tv.TvSeasonDetails
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

/**
 * Created by KSW on 2021-03-17
 */

class SeasonDetailDataSource(
    private val apiServiceApi: MovieServiceApi,
    private val compositeDisposable: CompositeDisposable
) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _tvSeasonDetailsResponse = MutableLiveData<TvSeasonDetails>()
    val tvSeasonDetailsResponse: LiveData<TvSeasonDetails>
        get() = _tvSeasonDetailsResponse

    private val _tvSeasonVideoResponse = MutableLiveData<VideoResponse>()
    val tvSeasonVideoResponse: LiveData<VideoResponse>
        get() = _tvSeasonVideoResponse

    private val _tvSeasonCastResponse = MutableLiveData<CastResponse>()
    val tvSeasonCastResponse: LiveData<CastResponse>
        get() = _tvSeasonCastResponse

    fun loadTvSeasonDetails(tvId: Int, seasonNumber: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            apiServiceApi.getTvSeason(tvId, seasonNumber)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _tvSeasonDetailsResponse.postValue(it)
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

    fun loadTvSeasonVideos(tvId: Int, seasonNumber: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            apiServiceApi.getTvSeasonVideos(tvId, seasonNumber)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _tvSeasonVideoResponse.postValue(it)
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

    fun loadTvSeasonCast(tvId: Int, seasonNumber: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            apiServiceApi.getTvSeasonCast(tvId, seasonNumber)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _tvSeasonCastResponse.postValue(it)
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