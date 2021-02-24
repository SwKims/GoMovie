package com.ksw.gomovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ksw.gomovie.model.main.Movie
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.repository.movie.MoviePagedListRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-02-23
 */

class MovieListViewModel(
    private val movieRepository: MoviePagedListRepository
//    private val type: String
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList: LiveData<PagedList<Movie>> by lazy {
        movieRepository.loadMovieList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun movieListEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}