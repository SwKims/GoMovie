package com.ksw.gomovie.repository.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.LivePagedListBuilder
import com.ksw.gomovie.model.main.Movie
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.util.Constants.Companion.END_PAGE
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-02-23
 */

class MoviePagedListRepository(private val apiServiceApi: MovieServiceApi) {

    lateinit var moviePageList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun loadMovieList(
        compositeDisposable: CompositeDisposable,
//        type: String
    ): LiveData<PagedList<Movie>> {

        movieDataSourceFactory = MovieDataSourceFactory(apiServiceApi, compositeDisposable)
        val config = PagedList.Config
            .Builder()
            .setEnablePlaceholders(false)
            .setPageSize(END_PAGE)
            .build()

        moviePageList = LivePagedListBuilder(movieDataSourceFactory, config)
            .build()

        return moviePageList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            movieDataSourceFactory.movieLiveDataSource, MovieDataSource::networkState
        )
    }

}