package com.ksw.gomovie.repository.cast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ksw.gomovie.model.detail.PeopleDetail
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

/**
 * Created by KSW on 2021-03-05
 */

class CastDetailDataSource(
    private val apiServiceApi: MovieServiceApi,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _peopleDetailResponse = MutableLiveData<PeopleDetail>()
    val peopleDetailResponse: LiveData<PeopleDetail>
        get() = _peopleDetailResponse

    fun loadPeopleDetails(peopleId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            apiServiceApi.getPeopleDetails(peopleId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _peopleDetailResponse.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                    }
                )?.let {
                    compositeDisposable.add(it)
                }
        } catch (e: Exception) {

        }
    }

}