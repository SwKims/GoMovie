package com.ksw.gomovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ksw.gomovie.model.response.CastResponse
import com.ksw.gomovie.model.response.VideoResponse
import com.ksw.gomovie.model.tv.TvSeasonDetails
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.repository.tvshow.season.SeasonDetailRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-03-17
 */

class TvSeasonDetailViewModel(
    private val seasonDetailRepository: SeasonDetailRepository,
    tvId: Int,
    seasonNumber: Int
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val tvSeasonDetails: LiveData<TvSeasonDetails> by lazy {
        seasonDetailRepository.loadingTvSeasonDetails(compositeDisposable, tvId, seasonNumber)
    }

    val tvSeasonVideos: LiveData<VideoResponse> by lazy {
        seasonDetailRepository.loadingTvSeasonVideos(compositeDisposable, tvId, seasonNumber)
    }

    val tvSeasonCast: LiveData<CastResponse> by lazy {
        seasonDetailRepository.loadingTvSeasonCast(compositeDisposable, tvId, seasonNumber)
    }

    val networkState: LiveData<NetworkState> by lazy {
        seasonDetailRepository.getTvSeasonDetailNetwork()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}