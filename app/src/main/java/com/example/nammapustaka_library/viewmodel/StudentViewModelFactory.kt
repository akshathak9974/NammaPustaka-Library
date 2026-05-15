package com.example.nammapustaka_library.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nammapustaka_library.data.StudentRepository

class StudentViewModelFactory(
    private val repository: StudentRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        return StudentViewModel(
            repository
        ) as T
    }
}