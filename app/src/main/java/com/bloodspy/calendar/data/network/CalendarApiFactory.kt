package com.bloodspy.calendar.data.network

import com.bloodspy.calendar.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CalendarApiFactory {
    private const val KEY_PARAM = "key"
    private const val BASE_URL =
        "http://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()

            val newUrl = originalRequest
                .url()
                .newBuilder()
                .addQueryParameter(KEY_PARAM, BuildConfig.LOCATION_API_KEY)
                .build()

            val newRequest = originalRequest
                .newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService: CalendarApiService = retrofit.create(CalendarApiService::class.java)
}