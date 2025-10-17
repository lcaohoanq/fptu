package com.fptu.lab.lab9

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    private fun loggingClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val mealApi: MealApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .client(loggingClient())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(MealApiService::class.java)
    }


    // NOTE: Replace baseUrl with your nutrition API base.
    val nutritionApi: NutritionApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://your-nutrition-api.example.com/")
            .client(loggingClient())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(NutritionApiService::class.java)
    }
}