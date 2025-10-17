package com.fptu.lab.lab9

import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    // Search by name
    @GET("search.php")
    suspend fun searchMeals(@Query("s") name: String): MealListResponse


    // Lookup full meal details by id
    @GET("lookup.php")
    suspend fun lookupMeal(@Query("i") id: String): MealDetailResponse
}