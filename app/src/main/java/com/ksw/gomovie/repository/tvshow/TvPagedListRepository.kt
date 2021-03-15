package com.ksw.gomovie.repository.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ksw.gomovie.model.main.TV
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.util.Constants.Companion.END_PAGE
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-03-15
 */

class TvPagedListRepository(private val apiServiceApi: MovieServiceApi) {

    lateinit var tvPageList: LiveData<PagedList<TV>>
    lateinit var tvDataSourceFactory: TvDataSourceFactory

    fun loadTvList(
        compositeDisposable: CompositeDisposable,
        type: String
    ): LiveData<PagedList<TV>> {

        tvDataSourceFactory = TvDataSourceFactory(apiServiceApi, compositeDisposable, type)
        val config = PagedList.Config
            .Builder()
            .setEnablePlaceholders(false)
            .setPageSize(END_PAGE)
            .build()

        tvPageList = LivePagedListBuilder(tvDataSourceFactory, config)
            .build()

        return tvPageList

    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap(
            tvDataSourceFactory.tvLiveDataSource , TvDataSource::networkState
        )
    }

}