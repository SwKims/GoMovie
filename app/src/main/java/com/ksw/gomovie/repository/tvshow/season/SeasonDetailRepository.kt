package com.ksw.gomovie.repository.tvshow.season

import androidx.lifecycle.LiveData
import com.ksw.gomovie.model.response.CastResponse
import com.ksw.gomovie.model.response.VideoResponse
import com.ksw.gomovie.model.tv.TvSeasonDetails
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-03-17
 */

class SeasonDetailRepository(private val apiServiceApi: MovieServiceApi) {

    lateinit var tvSeasonDetailDataSource: SeasonDetailDataSource

    fun loadingTvSeasonDetails(
        compositeDisposable: CompositeDisposable,
        tvId: Int,
        seasonNumber: Int
    ): LiveData<TvSeasonDetails> {
        tvSeasonDetailDataSource = SeasonDetailDataSource(
            apiServiceApi, compositeDisposable
        )
        tvSeasonDetailDataSource.loadTvSeasonDetails(tvId, seasonNumber)

        return tvSeasonDetailDataSource.tvSeasonDetailsResponse
    }

    fun loadingTvSeasonVideos(
        compositeDisposable: CompositeDisposable,
        tvId: Int,
        seasonNumber: Int
    ): LiveData<VideoResponse> {
        tvSeasonDetailDataSource = SeasonDetailDataSource(
            apiServiceApi, compositeDisposable
        )
        tvSeasonDetailDataSource.loadTvSeasonVideos(tvId, seasonNumber)

        return tvSeasonDetailDataSource.tvSeasonVideoResponse
    }

    fun loadingTvSeasonCast(
        compositeDisposable: CompositeDisposable,
        tvId: Int,
        seasonNumber: Int
    ): LiveData<CastResponse> {
        tvSeasonDetailDataSource = SeasonDetailDataSource(
            apiServiceApi, compositeDisposable
        )
        tvSeasonDetailDataSource.loadTvSeasonCast(tvId, seasonNumber)

        return tvSeasonDetailDataSource.tvSeasonCastResponse
    }

    fun getTvSeasonDetailNetwork(): LiveData<NetworkState> {
        return tvSeasonDetailDataSource.networkState
    }

}