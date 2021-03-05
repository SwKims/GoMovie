package com.ksw.gomovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ksw.gomovie.model.detail.PeopleDetail
import com.ksw.gomovie.model.main.PeopleImages
import com.ksw.gomovie.repository.cast.CastDetailRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-03-05
 */

class CastDetailViewModel(
    private val castDetailRepository: CastDetailRepository,
    peopleId: Int
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val peopleDetails: LiveData<PeopleDetail> by lazy {
        castDetailRepository.loadingPeopleDetails(compositeDisposable, peopleId)
    }

    val peopleImages: LiveData<PeopleImages> by lazy {
        castDetailRepository.loadingPeopleImages(compositeDisposable, peopleId)
    }



    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}