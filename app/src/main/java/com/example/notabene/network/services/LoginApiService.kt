package com.example.notabene.network.services

import com.example.notabene.model.user_model.ApiResponse
import com.example.notabene.model.user_model.LoginData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {

    @POST("/user/login")
    suspend fun login(@Body loginData: LoginData): ApiResponse
}