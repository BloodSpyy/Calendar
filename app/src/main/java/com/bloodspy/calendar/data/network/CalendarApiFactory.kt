package com.bloodspy.calendar.data.network

import com.bloodspy.calendar.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CalendarApiFactory {
    private const val CONTENT_TYPE_HEADER_PARAM = "Content-Type"
    private const val CONTENT_TYPE_HEADER_VALUE = "application/json"
    private const val ACCEPT_HEADER_PARAM = "Accept"
    private const val ACCEPT_HEADER_VALUE = "application/json"
    private const val AUTHORIZATION_HEADER_PARAM = "Authorization"
    private const val AUTHORIZATION_HEADER_VALUE = "Token ${BuildConfig.LOCATION_API_KEY}"

    private const val BASE_URL = "http://suggestions.dadata.ru/suggestions/api/4_1/rs/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val originalRequest = chain.request()

            val newRequest = originalRequest
                .newBuilder()
                .addHeader(CONTENT_TYPE_HEADER_PARAM, CONTENT_TYPE_HEADER_VALUE)
                .addHeader(ACCEPT_HEADER_PARAM, ACCEPT_HEADER_VALUE)
                .addHeader(AUTHORIZATION_HEADER_PARAM, AUTHORIZATION_HEADER_VALUE)
                .build()

            chain.proceed(newRequest)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val locationApiService: LocationApiService = retrofit.create(LocationApiService::class.java)
}