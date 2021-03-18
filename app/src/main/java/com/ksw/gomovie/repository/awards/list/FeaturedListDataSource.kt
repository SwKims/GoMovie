package com.ksw.gomovie.repository.awards.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ksw.gomovie.model.main.FeatureMovieLists
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

/**
 * Created by KSW on 2021-03-18
 */

class FeaturedListDataSource(
    private val apiServiceApi: MovieServiceApi,
    private val compositeDisposable: CompositeDisposable,
    private val listId: Int
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _featureMovieResponse = MutableLiveData<FeatureMovieLists>()
    val featureMovieResponse: LiveData<FeatureMovieLists>
        get() = _featureMovieResponse

    fun loadFeatureMovies() {
        _networkState.postValue(NetworkState.LOADING)

        try {
            apiServiceApi.getFeaturedMovies(listId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _featureMovieResponse.postValue(it)
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