package com.ksw.gomovie.repository.genre

import androidx.lifecycle.LiveData
import com.ksw.gomovie.model.response.GenresResponse
import com.ksw.gomovie.network.MovieServiceApi
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-03-03
 */

class GenreRepository(private val apiServiceApi: MovieServiceApi) {

    lateinit var genreDataSource: GenreDataSource

    fun loadingMovieGenre(
        compositeDisposable: CompositeDisposable
    ): LiveData<GenresResponse> {
        genreDataSource = GenreDataSource(
            apiServiceApi, compositeDisposable
        )

        genreDataSource.loadMovieGenre()

        return genreDataSource.genresResponse
    }

}