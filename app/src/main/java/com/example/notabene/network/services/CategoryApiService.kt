package com.example.notabene.network.services

import com.example.notabene.model.category_model.CategoryData
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Path


interface CategoryApiService {
    @GET("category/getAll")
    fun getCategories(

    ): Flowable<List<CategoryData>>
}