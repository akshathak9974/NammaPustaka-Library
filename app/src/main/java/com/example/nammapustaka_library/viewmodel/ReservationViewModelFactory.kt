package com.example.nammapustaka_library.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nammapustaka_library.data.ReservationRepository

class ReservationViewModelFactory(

    private val repository:
    ReservationRepository

) : ViewModelProvider.Factory {

    override fun <T : ViewModel>
            create(
        modelClass: Class<T>
    ): T {

        return ReservationViewModel(
            repository
        ) as T
    }
}