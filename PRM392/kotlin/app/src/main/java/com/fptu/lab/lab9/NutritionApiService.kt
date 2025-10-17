package com.fptu.lab.lab9

import retrofit2.http.Body
import retrofit2.http.POST

interface NutritionApiService {
    // This is a placeholder POST; Edamam uses application/x-www-form-urlencoded in some flows
// For lab, we'll assume a POST that accepts JSON list of ingredient strings and returns totals.
    @POST("analyze")
    suspend fun analyzeNutrition(@Body request: NutritionRequest): NutritionResponse
}