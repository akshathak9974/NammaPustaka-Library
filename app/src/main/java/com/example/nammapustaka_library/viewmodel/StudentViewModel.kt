package com.example.nammapustaka_library.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nammapustaka_library.data.StudentRepository
import com.example.nammapustaka_library.model.Student
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StudentViewModel(
    private val repository: StudentRepository
) : ViewModel() {

    val students =
        repository.getStudents()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun registerStudent(
        student: Student
    ) {

        viewModelScope.launch {

            repository.registerStudent(student)
        }
    }

    suspend fun loginStudent(
        id: String,
        password: String
    ): Student? {

        return repository.loginStudent(
            id,
            password
        )
    }
}