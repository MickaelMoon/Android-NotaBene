package com.example.notabene.repositories

import com.example.notabene.model.user_model.ApiResponse
import com.example.notabene.model.user_model.LoginData
import com.example.notabene.model.user_model.SignupResponse
import com.example.notabene.network.services.LoginApiService

class SignupRepository(
    private val loginService: LoginApiService
) {
    suspend fun signup(username: String, password: String): SignupResponse {
        return this.loginService.signup(LoginData(username, password))
    }
}