package com.ksw.gomovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ksw.gomovie.model.detail.PeopleExternalDetail
import com.ksw.gomovie.model.response.VideoResponse
import com.ksw.gomovie.model.main.MovieDetail
import com.ksw.gomovie.model.response.CastResponse
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.repository.moviedetail.MovieDetailRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-02-24
 */

class MovieDetailViewModel(
    private val movieDetailRepository: MovieDetailRepository, movieId: Int
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails: LiveData<MovieDetail> by lazy {
        movieDetailRepository.loadingMovieDetails(compositeDisposable, movieId)
    }

    val videoDetails: LiveData<VideoResponse> by lazy {
        movieDetailRepository.loadingMovieVideos(compositeDisposable, movieId)
    }

    val castDetails: LiveData<CastResponse> by lazy {
        movieDetailRepository.loadingCastDetails(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieDetailRepository.getMovieDetailNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}