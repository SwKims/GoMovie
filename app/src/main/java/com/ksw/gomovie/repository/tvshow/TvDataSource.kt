package com.ksw.gomovie.repository.tvshow

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.ksw.gomovie.model.main.TV
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.util.Constants.Companion.FIRST_PAGE
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by KSW on 2021-03-12
 */

class TvDataSource(
    private val apiServiceApi: MovieServiceApi,
    private val compositeDisposable: CompositeDisposable,
    private val type: String
) : PageKeyedDataSource<Int, TV>() {

    var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TV>
    ) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiServiceApi.getTVList(type, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.tvList, null, page + 1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                    }
                )
        )

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TV>) {


    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TV>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiServiceApi.getTVList(type, params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.totalPages >= params.key) {
                            callback.onResult(it.tvList, params.key + 1)
                            networkState.postValue(NetworkState.LOADED)
                        } else {
                            networkState.postValue(NetworkState.END)
                        }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                    }
                )
        )
    }


}