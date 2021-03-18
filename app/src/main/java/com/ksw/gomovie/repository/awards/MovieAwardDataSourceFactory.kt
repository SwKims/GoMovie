package com.ksw.gomovie.repository.awards

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ksw.gomovie.model.main.Movie
import com.ksw.gomovie.network.MovieServiceApi
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-03-18
 */

class MovieAwardDataSourceFactory(
    private val apiServiceApi: MovieServiceApi,
    private val compositeDisposable: CompositeDisposable,
    private val id: Int
) : DataSource.Factory<Int, Movie>() {

    val awardsLiveDataSource = MutableLiveData<MovieAwardDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val awardsDataSource = MovieAwardDataSource(
            apiServiceApi, compositeDisposable, id
        )

        awardsLiveDataSource.postValue(awardsDataSource)
        return awardsDataSource

    }


}


