package com.ksw.gomovie.repository.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ksw.gomovie.model.response.MovieResponse
import com.ksw.gomovie.model.response.RecommendResponse
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

/**
 * Created by KSW on 2021-03-04
 */

class MovieRecommendDataSource(
    private val apiServiceApi: MovieServiceApi,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _movieRecommendResponse = MutableLiveData<MovieResponse>()
    val movieRecommendResponse: LiveData<MovieResponse>
        get() = _movieRecommendResponse

    fun loadRecommendMovies(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            apiServiceApi.getMovieRecommendations(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _movieRecommendResponse.postValue(it)
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