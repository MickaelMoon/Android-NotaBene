package com.example.notabene.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notabene.R
import com.example.notabene.di.injectModuleDependencies
import com.example.notabene.di.parseConfigurationAndAddItToInjectionModules

class CreateActivity(): AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modify_layout)

        parseConfigurationAndAddItToInjectionModules()
        injectModuleDependencies(this@CreateActivity)

    }
}