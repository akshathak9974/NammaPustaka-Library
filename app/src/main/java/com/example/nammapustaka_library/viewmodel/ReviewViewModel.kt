package com.example.nammapustaka_library.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nammapustaka_library.data.ReviewRepository
import com.example.nammapustaka_library.model.Review
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val repository: ReviewRepository,
    private val bookId: Int
) : ViewModel() {

    val reviews =
        repository
            .getReviewsForBook(bookId)
            .stateIn(
                scope = viewModelScope,

                started =
                    SharingStarted
                        .WhileSubscribed(5000),

                initialValue = emptyList()
            )

    fun addReview(
        review: Review
    ) {

        viewModelScope.launch {

            repository.addReview(review)
        }
    }
}