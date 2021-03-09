package com.ksw.gomovie.repository.cast

import androidx.lifecycle.LiveData
import com.ksw.gomovie.model.detail.MovieCredits
import com.ksw.gomovie.model.detail.PeopleDetail
import com.ksw.gomovie.model.main.PeopleImages
import com.ksw.gomovie.network.MovieServiceApi
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-03-05
 */

class CastDetailRepository(private val apiServiceApi: MovieServiceApi) {

    lateinit var castDetailDataSource: CastDetailDataSource

    fun loadingPeopleDetails(
        compositeDisposable: CompositeDisposable,
        peopleId: Int
    ): LiveData<PeopleDetail> {
        castDetailDataSource = CastDetailDataSource(
            apiServiceApi, compositeDisposable
        )

        castDetailDataSource.loadPeopleDetails(peopleId)

        return castDetailDataSource.peopleDetailResponse
    }

    fun loadingPeopleImages(
        compositeDisposable: CompositeDisposable,
        peopleId: Int
    ): LiveData<PeopleImages> {
        castDetailDataSource = CastDetailDataSource(
            apiServiceApi, compositeDisposable
        )

        castDetailDataSource.loadPeopleImages(peopleId)

        return castDetailDataSource.peopleImages
    }

    fun loadingMovieCredits(
        compositeDisposable: CompositeDisposable,
        peopleId: Int
    ): LiveData<MovieCredits> {
        castDetailDataSource = CastDetailDataSource(
            apiServiceApi, compositeDisposable
        )
        castDetailDataSource.loadMovieCredits(peopleId)

        return castDetailDataSource.movieCredits
    }

}