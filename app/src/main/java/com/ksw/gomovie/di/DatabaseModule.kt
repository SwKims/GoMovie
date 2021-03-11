//package com.ksw.gomovie.di
//
//import android.content.Context
//import androidx.room.Room
//import com.ksw.gomovie.database.MovieDao
//import com.ksw.gomovie.database.MovieDatabase
//import com.ksw.gomovie.util.Constants.Companion.DATABASE_NAME
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ApplicationComponent
//import dagger.hilt.android.qualifiers.ApplicationContext
//import javax.inject.Singleton
//
///**
// * Created by KSW on 2021-03-10
// */
//
//
//@Module
//@InstallIn(ApplicationComponent::class)
//object DatabaseModule {
//
//    @Singleton
//    @Provides
//    fun provideDatabase(
//        @ApplicationContext context: Context
//    ) = Room.databaseBuilder(
//        context, MovieDatabase::class.java, DATABASE_NAME
//    ).build()
//
//    @Singleton
//    @Provides
//    fun provideMovieDao(database: MovieDatabase): MovieDao = database.getMovieDao()
//
//
//}