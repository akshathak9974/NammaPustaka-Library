package com.example.nammapustaka_library.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nammapustaka_library.data.ReviewRepository

class ReviewViewModelFactory(
    private val repository: ReviewRepository,
    private val bookId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        return ReviewViewModel(
            repository,
            bookId
        ) as T
    }
}