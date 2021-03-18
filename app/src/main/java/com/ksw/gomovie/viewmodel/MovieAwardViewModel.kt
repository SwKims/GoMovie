package com.ksw.gomovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ksw.gomovie.model.main.Movie
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.repository.awards.MovieAwardPagedList
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-03-18
 */

class MovieAwardViewModel(
    private val movieAwardPagedList: MovieAwardPagedList,
    private val id: Int
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val awardsList: LiveData<PagedList<Movie>> by lazy {
        movieAwardPagedList.loadMovieList(compositeDisposable, id)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieAwardPagedList.getNetworkState()
    }

    fun listEmpty(): Boolean {
        return awardsList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}