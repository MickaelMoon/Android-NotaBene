package com.example.notabene.repositories

import com.example.notabene.model.user_model.ApiResponse
import com.example.notabene.model.user_model.LoginData
import com.example.notabene.network.services.LoginApiService
import retrofit2.Call

class LoginRepository(
    private val loginService: LoginApiService
) {
    suspend fun login(username: String, password: String): ApiResponse {
        return this.loginService.login(LoginData(username, password))
    }
}