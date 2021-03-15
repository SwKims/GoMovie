package com.ksw.gomovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ksw.gomovie.model.detail.TvDetail
import com.ksw.gomovie.model.response.CastResponse
import com.ksw.gomovie.model.response.VideoResponse
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.repository.tvshow.detail.TvDetailRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-03-15
 */

class TvDetailViewModel(
    private val tvDetailRepository: TvDetailRepository, tvId: Int
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val tvDetails: LiveData<TvDetail> by lazy {
        tvDetailRepository.loadingTvDetails(compositeDisposable, tvId)
    }

    val tvVideoDetails: LiveData<VideoResponse> by lazy {
        tvDetailRepository.loadingTvVideos(compositeDisposable, tvId)
    }

    val tvCastDetails: LiveData<CastResponse> by lazy {
        tvDetailRepository.loadingTvCastDetails(compositeDisposable, tvId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        tvDetailRepository.getMovieDetailNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}