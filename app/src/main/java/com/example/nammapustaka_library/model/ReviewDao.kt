package com.example.nammapustaka_library.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {

    @Insert
    suspend fun insertReview(
        review: Review
    )

    @Query(
        """
        SELECT * FROM reviews
        WHERE bookId = :bookId
        """
    )
    fun getReviewsForBook(
        bookId: Int
    ): Flow<List<Review>>
}