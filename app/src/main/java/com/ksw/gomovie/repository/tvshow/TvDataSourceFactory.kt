package com.ksw.gomovie.repository.tvshow

import androidx.lifecycle.MutableLiveData
import com.ksw.gomovie.model.main.TV
import com.ksw.gomovie.network.MovieServiceApi
import io.reactivex.disposables.CompositeDisposable
import androidx.paging.DataSource

/**
 * Created by KSW on 2021-03-15
 */

class TvDataSourceFactory(
    private val apiServiceApi: MovieServiceApi,
    private val compositeDisposable: CompositeDisposable,
    private val type: String
): DataSource.Factory<Int, TV>() {

    val tvLiveDataSource = MutableLiveData<TvDataSource>()

    override fun create(): DataSource<Int, TV> {

        val tvDataSource = TvDataSource(
            apiServiceApi, compositeDisposable, type
        )
        tvLiveDataSource.postValue(tvDataSource)

        return tvDataSource

    }
}