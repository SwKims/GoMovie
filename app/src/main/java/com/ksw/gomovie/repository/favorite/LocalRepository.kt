//package com.ksw.gomovie.repository.favorite
//
//import com.ksw.gomovie.database.FavoritesEntity
//import com.ksw.gomovie.database.MovieDao
//import com.ksw.gomovie.model.main.Movie
//import kotlinx.coroutines.flow.Flow
//import javax.inject.Inject
//
///**
// * Created by KSW on 2021-03-10
// */
//
//class LocalRepository @Inject constructor(
//    private val movieDao: MovieDao
//) {
//
//    fun readFavoriteMovies() : Flow<List<FavoritesEntity>> {
//        return movieDao.readFavoriteMovies()
//    }
//
//    suspend fun insertFavoriteMovies(favoritesEntity: FavoritesEntity) {
//        movieDao.insertFavoriteMovie(favoritesEntity)
//    }
//
//    suspend fun deleteFavoriteMovie(favoritesEntity: FavoritesEntity) {
//        movieDao.deleteFavoriteMovie(favoritesEntity)
//    }
//
//    suspend fun deleteAllFavoriteMovies() {
//        movieDao.deleteAllFavoriteMovies()
//    }
//
//}