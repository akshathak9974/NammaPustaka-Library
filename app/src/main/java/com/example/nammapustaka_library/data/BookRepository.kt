package com.example.nammapustaka_library.data

import com.example.nammapustaka_library.model.Book
import com.example.nammapustaka_library.model.BookDao

class BookRepository(
    private val bookDao: BookDao
) {

    fun getBooks() =
        bookDao.getBooks()

    fun getLeaderboard() =
        bookDao.getLeaderboard()

    suspend fun addBook(
        name: String,
        author: String,
        category: String,
        imageUri: String
    ) {

        bookDao.insertBook(

            Book(
                name = name,
                author = author,
                category = category,
                imageUri = imageUri
            )
        )
    }

    suspend fun deleteBook(
        book: Book
    ) {

        bookDao.deleteBook(book)
    }

    suspend fun issueBook(
        bookId: Int,
        studentName: String,
        studentId: String
    ) {

        bookDao.issueBook(

            bookId = bookId,

            studentName = studentName,

            studentId = studentId,

            issueDate = "15/05/2026",

            returnDate = "30/05/2026"
        )
    }

    suspend fun returnBook(
        bookId: Int,
        overdueDays: Int,
        fine: Int
    ) {

        bookDao.returnBook(
            bookId,
            overdueDays,
            fine
        )
    }
    suspend fun getBookById(
        bookId: Int
    ): Book? {

        return bookDao.getBookById(bookId)
    }
}