package com.example.notabene.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.notabene.model.user_model.ApiResponse
import com.example.notabene.repositories.LoginRepository
import okhttp3.Call
import okhttp3.RequestBody

class LoginViewModel(
    private val loginRepo: LoginRepository
): ViewModel() {

    suspend fun login(login: String, password: String): ApiResponse {
        return this.loginRepo.login(login, password)
    }
}