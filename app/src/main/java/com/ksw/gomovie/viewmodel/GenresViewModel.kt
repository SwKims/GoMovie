package com.ksw.gomovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ksw.gomovie.model.response.GenresResponse
import com.ksw.gomovie.repository.genre.GenreRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-03-03
 */

class GenresViewModel(private val genreRepository: GenreRepository): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieGenreList: LiveData<GenresResponse> by lazy {
        genreRepository.loadingMovieGenre(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}