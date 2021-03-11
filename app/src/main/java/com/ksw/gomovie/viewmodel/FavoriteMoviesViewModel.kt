//package com.ksw.gomovie.viewmodel
//
//import android.app.Application
//import androidx.hilt.Assisted
//import androidx.hilt.lifecycle.ViewModelInject
//import androidx.lifecycle.*
//import com.ksw.gomovie.database.FavoritesEntity
//import com.ksw.gomovie.model.main.Movie
//import com.ksw.gomovie.repository.Repository
//import com.ksw.gomovie.util.Constants.Companion.MOVIE_ID
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
///**
// * Created by KSW on 2021-03-10
// */
//
//class FavoriteMoviesViewModel @ViewModelInject constructor(
//    private val repository: Repository,
//    application: Application
//) : AndroidViewModel(application) {
//
//    val readFavoriteMovies: LiveData<List<FavoritesEntity>> =
//        repository.localRepository.readFavoriteMovies().asLiveData()
//
//    fun insertFavoriteMovies(favoritesEntity: FavoritesEntity) =
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.localRepository.insertFavoriteMovies(favoritesEntity)
//        }
//
//    fun deleteFavoriteMovie(favoritesEntity: FavoritesEntity) =
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.localRepository.deleteFavoriteMovie(favoritesEntity)
//        }
//
//    fun deleteAllFavoriteMovies() =
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.localRepository.deleteAllFavoriteMovies()
//        }
//
//}