package com.ksw.gomovie.network

import com.ksw.gomovie.util.Constants.Companion.API_KEY
import com.ksw.gomovie.util.Constants.Companion.BASE_URL
import com.ksw.gomovie.util.Constants.Companion.LANGUAGE
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by KSW on 2021-02-23
 */

object NetworkModule {

    fun getClient(): MovieServiceApi {

        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter(
                            "api_key", API_KEY
                    )
                    .addQueryParameter("language", LANGUAGE)
                    .build()

            val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build()

        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieServiceApi::class.java)
    }


}