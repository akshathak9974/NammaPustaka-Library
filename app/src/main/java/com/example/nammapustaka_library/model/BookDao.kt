package com.example.nammapustaka_library.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.nammapustaka_library.model.LeaderboardItem

@Dao
interface BookDao {

    @Insert
    suspend fun insertBook(
        book: Book
    )

    @Delete
    suspend fun deleteBook(
        book: Book
    )

    @Query(
        "SELECT * FROM books"
    )
    fun getBooks():
            Flow<List<Book>>

    @Query(
        """
        UPDATE books
        SET
        isIssued = 1,
        issuedTo = :studentName,
        issuedStudentId = :studentId,
        issueDate = :issueDate,
        returnDate = :returnDate
        WHERE id = :bookId
        """
    )
    suspend fun issueBook(

        bookId: Int,

        studentName: String,

        studentId: String,

        issueDate: String,

        returnDate: String
    )

    @Query(
        """
        UPDATE books
        SET
        isIssued = 0,
        overdueDays = :overdueDays,
        fine = :fine
        WHERE id = :bookId
        """
    )
    suspend fun returnBook(

        bookId: Int,

        overdueDays: Int,

        fine: Int
    )

    @Query(
        """
        SELECT * FROM books
        WHERE id = :bookId
        LIMIT 1
        """
    )
    suspend fun getBookById(
        bookId: Int
    ): Book?

    @Query(
        """
        SELECT issuedTo as studentName,
        COUNT(*) as totalBooks
        FROM books
        WHERE issuedTo != ''
        GROUP BY issuedTo
        ORDER BY totalBooks DESC
        """
    )
    fun getLeaderboard():
            Flow<List<LeaderboardItem>>
}