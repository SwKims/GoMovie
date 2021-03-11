//package com.ksw.gomovie.database
//
//import androidx.room.*
//import kotlinx.coroutines.flow.Flow
//
///**
// * Created by KSW on 2021-03-10
// */
//
//@Dao
//interface MovieDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertFavoriteMovie(favoritesEntity: FavoritesEntity)
//
//    @Query("SELECT * FROM favorite_movie ORDER BY id ASC")
//    fun readFavoriteMovies(): Flow<List<FavoritesEntity>>
//
//    @Delete
//    suspend fun deleteFavoriteMovie(favoritesEntity: FavoritesEntity)
//
//    @Query("DELETE FROM favorite_movie")
//    suspend fun deleteAllFavoriteMovies()
//
////    @Query ("SELECT * FROM favorite_movie ORDER BY id DESC")
////    fun getFavoriteMovies() : LiveData<List<Movie>>
////
////    @Query ("SELECT EXISTS(SELECT * FROM favorite_movie WHERE id=:id)")
////    suspend fun isMovieExists(id: Int): Boolean
//
//}