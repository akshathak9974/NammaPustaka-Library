package com.example.nammapustaka_library.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nammapustaka_library.data.BookRepository
import com.example.nammapustaka_library.model.Book
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class BookViewModel(
    private val repository: BookRepository
) : ViewModel() {

    val books =
        repository.getBooks()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    val leaderboard =
        repository.getLeaderboard()
            .stateIn(

                scope = viewModelScope,

                started =
                    SharingStarted
                        .WhileSubscribed(5000),

                initialValue =
                    emptyList()
            )

    fun addBook(
        name: String,
        author: String,
        category: String,
        imageUri: String
    ){

        viewModelScope.launch {

            repository.addBook(
                name,
                author,
                category,
                imageUri
            )
        }
    }

    fun deleteBook(
        book: Book
    ) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }

    fun issueBook(
        bookId: Int,
        studentName: String,
        studentId: String
    ) {

        viewModelScope.launch {

            repository.issueBook(
                bookId,
                studentName,
                studentId
            )
        }
    }
    suspend fun getBookById(
        bookId: Int
    ): Book? {

        return repository.getBookById(bookId)
    }

    fun returnBook(bookId: Int) {

        viewModelScope.launch {

            val book = books.value.find {
                it.id == bookId
            }

            if (book != null) {

                val format = SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                )

                val today =
                    format.parse(
                        format.format(Date())
                    )

                val returnDate =
                    format.parse(book.returnDate)

                val diff =
                    today.time - returnDate.time

                val overdueDays =
                    TimeUnit.MILLISECONDS.toDays(diff)
                        .toInt()

                val finalOverdueDays =
                    if (overdueDays > 0)
                        overdueDays
                    else
                        0

                val fine =
                    finalOverdueDays * 10

                repository.returnBook(
                    bookId = bookId,
                    overdueDays = finalOverdueDays,
                    fine = fine
                )
            }
        }
    }
}

