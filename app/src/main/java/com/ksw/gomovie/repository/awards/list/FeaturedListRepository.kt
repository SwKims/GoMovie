package com.ksw.gomovie.repository.awards.list

import androidx.lifecycle.LiveData
import com.ksw.gomovie.model.main.FeatureMovieLists
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-03-18
 */

class FeaturedListRepository(
    private val apiServiceApi: MovieServiceApi
) {
    lateinit var featuredListDataSource: FeaturedListDataSource

    fun loadingFeatureMovies(
        compositeDisposable: CompositeDisposable,
        listId: Int
    ): LiveData<FeatureMovieLists> {
        featuredListDataSource = FeaturedListDataSource(
            apiServiceApi, compositeDisposable, listId
        )
        featuredListDataSource.loadFeatureMovies()

        return featuredListDataSource.featureMovieResponse
    }

    fun networkState(): LiveData<NetworkState> {
        return featuredListDataSource.networkState
    }

}