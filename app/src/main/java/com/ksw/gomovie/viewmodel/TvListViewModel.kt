package com.ksw.gomovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ksw.gomovie.model.main.TV
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.repository.tvshow.TvPagedListRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-03-15
 */

class TvListViewModel(
    private val tvRepository: TvPagedListRepository,
    private val type: String
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val tvPagedList: LiveData<PagedList<TV>> by lazy {
        tvRepository.loadTvList(compositeDisposable, type)
    }

    val networkState: LiveData<NetworkState> by lazy {
        tvRepository.getNetworkState()
    }

    fun tvListEmpty(): Boolean {
        return tvPagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}