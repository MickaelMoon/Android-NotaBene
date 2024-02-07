package com.example.notabene.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.notabene.R
import com.example.notabene.di.injectModuleDependencies
import com.example.notabene.di.parseConfigurationAndAddItToInjectionModules
import com.example.notabene.model.user_model.ApiResponse
import com.example.notabene.network.services.LoginApiService
import com.example.notabene.repositories.LoginRepository
import com.example.notabene.viewmodel.LoginViewModel
import com.example.notabene.viewmodel.NotesViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import javax.security.auth.callback.Callback

class LoginActivity: AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connection_layout)

        parseConfigurationAndAddItToInjectionModules()
        injectModuleDependencies(this@LoginActivity)

        // get reference to all views
        var et_user_name = findViewById(R.id.et_user_name) as EditText
        var et_password = findViewById(R.id.et_password) as EditText
        var btn_submit = findViewById(R.id.btn_submit) as Button



        // set on-click listener
        btn_submit.setOnClickListener {
            val login = et_user_name.text.toString()
            val password = et_password.text.toString()
            lifecycleScope.launch {
                try {
                    val response = loginViewModel.login(login, password)
                    if(response.message == "Success") {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("userId", response.userId)
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}