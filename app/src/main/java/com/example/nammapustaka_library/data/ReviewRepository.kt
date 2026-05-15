package com.example.nammapustaka_library.data

import com.example.nammapustaka_library.model.Review
import com.example.nammapustaka_library.model.ReviewDao

class ReviewRepository(
    private val dao: ReviewDao
) {

    suspend fun addReview(
        review: Review
    ) {

        dao.insertReview(review)
    }

    fun getReviewsForBook(
        bookId: Int
    ) = dao.getReviewsForBook(bookId)
}