package com.example.notabene.viewmodel

import androidx.lifecycle.ViewModel
import com.example.notabene.model.user_model.ApiResponse
import com.example.notabene.model.user_model.SignupResponse
import com.example.notabene.repositories.SignupRepository

class SignupViewModel(
    private val signupRepo: SignupRepository
): ViewModel() {
    suspend fun signup(username: String, password: String): SignupResponse {
        return this.signupRepo.signup(username, password)
    }
}