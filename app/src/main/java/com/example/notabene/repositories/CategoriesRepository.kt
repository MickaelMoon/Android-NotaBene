package com.example.notabene.repositories

import com.example.notabene.model.category_model.CategoryData
import com.example.notabene.model.note_model.NoteData
import com.example.notabene.network.services.CategoryApiService
import io.reactivex.rxjava3.core.Flowable


class CategoriesRepository (
    private val categoryService: CategoryApiService
){
    fun getCategories() : Flowable<List<CategoryData>> {
        return categoryService.getCategories().map {
            it
        }
    }
}