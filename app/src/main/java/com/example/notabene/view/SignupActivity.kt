package com.example.notabene.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.notabene.R
import com.example.notabene.di.injectModuleDependencies
import com.example.notabene.di.parseConfigurationAndAddItToInjectionModules
import com.example.notabene.viewmodel.SignupViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupActivity(): AppCompatActivity() {
    private val signupViewModel: SignupViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_layout)


        parseConfigurationAndAddItToInjectionModules()
        injectModuleDependencies(this@SignupActivity)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val username = etUsername.text.toString()
                    val password = etPassword.text.toString()
                    signupViewModel.signup(username, password)
                    Toast.makeText(this@SignupActivity, "Inscription réussi", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Toast.makeText(this@SignupActivity, "Problème lors de l'inscription", Toast.LENGTH_LONG).show()
                }
            }
            finish()
        }
    }
}