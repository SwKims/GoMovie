package com.ksw.gomovie.repository.awards

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ksw.gomovie.model.main.Movie
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.util.Constants.Companion.END_PAGE
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-03-18
 */

class MovieAwardPagedList(private val apiServiceApi: MovieServiceApi) {

    lateinit var moviePageList: LiveData<PagedList<Movie>>
    lateinit var awardsDataSourceFactory: MovieAwardDataSourceFactory

    fun loadMovieList(
        compositeDisposable: CompositeDisposable,
        id: Int
    ): LiveData<PagedList<Movie>> {

        awardsDataSourceFactory = MovieAwardDataSourceFactory(
            apiServiceApi, compositeDisposable, id
        )
        val config = PagedList.Config
            .Builder()
            .setEnablePlaceholders(false)
            .setPageSize(END_PAGE)
            .build()

        moviePageList = LivePagedListBuilder(awardsDataSourceFactory, config).build()
        return moviePageList

    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap(
            awardsDataSourceFactory.awardsLiveDataSource, MovieAwardDataSource::networkState
        )
    }

}