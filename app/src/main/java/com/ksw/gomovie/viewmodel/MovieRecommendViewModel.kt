package com.ksw.gomovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ksw.gomovie.model.response.MovieResponse
import com.ksw.gomovie.model.response.RecommendResponse
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.repository.recommend.MovieRecommendRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-03-04
 */

class MovieRecommendViewModel(
    private val movieMovieRecommendRepository: MovieRecommendRepository,
    private val type: String
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieRecommendDetails: LiveData<MovieResponse> by lazy {
        movieMovieRecommendRepository.loadingMovieRecommends(compositeDisposable, type)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieMovieRecommendRepository.getMovieRecommendNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}