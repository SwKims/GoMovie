package com.ksw.gomovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ksw.gomovie.model.main.FeatureMovieLists
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.repository.awards.list.FeaturedListRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-03-18
 */

class FeatureViewModel(
    private val featuredListRepository: FeaturedListRepository,
    private val listId: Int
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val featuredMovieList: LiveData<FeatureMovieLists> by lazy {
        featuredListRepository.loadingFeatureMovies(compositeDisposable, listId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        featuredListRepository.networkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}