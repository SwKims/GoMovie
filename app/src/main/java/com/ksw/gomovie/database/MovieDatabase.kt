//package com.ksw.gomovie.database
//
//import androidx.room.Database
//import androidx.room.RoomDatabase
//import com.ksw.gomovie.model.main.Movie
//
///**
// * Created by KSW on 2021-03-10
// */
//
//@Database(
//    entities = [Movie::class],
//    version = 3,
//    exportSchema = false
//)
//
//abstract class MovieDatabase : RoomDatabase() {
//    abstract fun getMovieDao(): MovieDao
//}