package com.ksw.gomovie.repository.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ksw.gomovie.model.response.GenresResponse
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

/**
 * Created by KSW on 2021-03-03
 */

class GenreDataSource(
    private val apiServiceApi: MovieServiceApi,
    private val compositeDisposable: CompositeDisposable
) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _genresResponse = MutableLiveData<GenresResponse>()
    val genresResponse: LiveData<GenresResponse>
        get() = _genresResponse

    fun loadMovieGenre() {
        _networkState.postValue(NetworkState.LOADING)

        try {
            apiServiceApi.getMovieGenresList()
                ?.subscribeOn(Schedulers.io())
                ?.subscribe(
                    {
                        _genresResponse.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                    }
                )?.let {
                    compositeDisposable.add(
                        it
                    )
                }
        } catch (e: Exception) {

        }

    }


}