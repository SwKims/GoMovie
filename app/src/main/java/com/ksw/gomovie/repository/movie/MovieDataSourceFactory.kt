package com.ksw.gomovie.repository.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ksw.gomovie.model.main.Movie
import com.ksw.gomovie.network.MovieServiceApi
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-02-23
 */

class MovieDataSourceFactory(
    private val apiServiceApi: MovieServiceApi,
    private val compositeDisposable: CompositeDisposable,
    private val type: String
) : DataSource.Factory<Int, Movie>() {

    val movieLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(
            apiServiceApi, compositeDisposable, type
        )

        movieLiveDataSource.postValue(movieDataSource)

        return movieDataSource

    }


}