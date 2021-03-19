package com.ksw.gomovie.repository.cast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ksw.gomovie.model.detail.MovieCredits
import com.ksw.gomovie.model.detail.PeopleDetail
import com.ksw.gomovie.model.detail.PeopleExternalDetail
import com.ksw.gomovie.model.main.PeopleImages
import com.ksw.gomovie.model.tv.TvCredits
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

    private val _peopleImages = MutableLiveData<PeopleImages>()
    val peopleImages: LiveData<PeopleImages>
        get() = _peopleImages

    private val _movieCredits = MutableLiveData<MovieCredits>()
    val movieCredits: LiveData<MovieCredits>
        get() = _movieCredits

    private val _peopleDetailExternal = MutableLiveData<PeopleDetail>()
    val peopleDetailExternal: LiveData<PeopleDetail>
        get() = _peopleDetailExternal

    private val _tvCreditsResponse = MutableLiveData<TvCredits>()
    val tvCreditsResponse: LiveData<TvCredits>
        get() = _tvCreditsResponse

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

    fun loadPeopleImages(peopleId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            apiServiceApi.getPeopleImages(peopleId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _peopleImages.postValue(it)
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

    fun loadMovieCredits(peopleId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            apiServiceApi.getMovieCredits(peopleId)
                ?.subscribeOn(Schedulers.io())
                ?.subscribe(
                    {
                        _movieCredits.postValue(it)
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

    fun loadPeopleExternalDetail(peopleId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            apiServiceApi.getPeopleSNS(peopleId)
                ?.subscribeOn(Schedulers.io())
                ?.subscribe(
                    {
                        _peopleDetailExternal.postValue(it)
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

    fun loadTvCredits(peopleId: Int) {
        _networkState.postValue(NetworkState.LOADING)
        try {
            apiServiceApi.getTvCredits(peopleId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _tvCreditsResponse.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                    }
                ).let {
                    compositeDisposable.add(it)
                }
        } catch (e: Exception) {

        }
    }


}