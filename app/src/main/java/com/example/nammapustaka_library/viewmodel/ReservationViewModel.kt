package com.example.nammapustaka_library.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nammapustaka_library.data.ReservationRepository
import kotlinx.coroutines.launch

class ReservationViewModel(

    private val repository:
    ReservationRepository

) : ViewModel() {

    fun reserveBook(

        bookId: Int,

        bookName: String,

        studentName: String,

        studentId: String
    ) {

        viewModelScope.launch {

            repository.reserveBook(

                bookId,

                bookName,

                studentName,

                studentId
            )
        }
    }
}