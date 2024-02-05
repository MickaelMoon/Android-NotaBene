package com.example.notabene.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notabene.R
import com.example.notabene.di.injectModuleDependencies
import com.example.notabene.di.parseConfigurationAndAddItToInjectionModules
import com.example.notabene.viewmodel.ModifyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ModifyActivity(): AppCompatActivity() {
//    private val modifyViewModel: ModifyViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modify_layout)

        parseConfigurationAndAddItToInjectionModules()
        injectModuleDependencies(this@ModifyActivity)

    }
}