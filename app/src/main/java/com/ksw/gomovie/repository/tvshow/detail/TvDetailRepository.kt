package com.ksw.gomovie.repository.tvshow.detail

import androidx.lifecycle.LiveData
import com.ksw.gomovie.model.detail.TvDetail
import com.ksw.gomovie.model.response.CastResponse
import com.ksw.gomovie.model.response.VideoResponse
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-03-15
 */

class TvDetailRepository(private val apiServiceApi: MovieServiceApi) {

    lateinit var tvDetailDataSource: TvDetailDataSource

    fun loadingTvDetails(
        compositeDisposable: CompositeDisposable,
        tvId: Int
    ): LiveData<TvDetail> {
        tvDetailDataSource =
            TvDetailDataSource(
                apiServiceApi, compositeDisposable
            )
        tvDetailDataSource.loadTvDetails(tvId)

        return tvDetailDataSource.tvDetailsResponse
    }

    fun loadingTvVideos(
        compositeDisposable: CompositeDisposable,
        tvId: Int
    ): LiveData<VideoResponse> {
        tvDetailDataSource =
            TvDetailDataSource(
                apiServiceApi, compositeDisposable
            )
        tvDetailDataSource.loadTvVideos(tvId)

        return tvDetailDataSource.tvVideosResponse
    }

    fun loadingTvCastDetails(
        compositeDisposable: CompositeDisposable,
        tvId: Int
    ): LiveData<CastResponse> {
        tvDetailDataSource =
            TvDetailDataSource(
                apiServiceApi, compositeDisposable
            )
        tvDetailDataSource.loadTvCastDetails(tvId)

        return tvDetailDataSource.tvCastResponse
    }

    fun getMovieDetailNetworkState(): LiveData<NetworkState> {
        return tvDetailDataSource.networkState
    }

}