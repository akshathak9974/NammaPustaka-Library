package com.example.nammapustaka_library.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nammapustaka_library.data.BookRepository

class BookViewModelFactory(
    private val repository: BookRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return BookViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}