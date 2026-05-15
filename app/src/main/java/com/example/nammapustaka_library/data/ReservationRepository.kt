package com.example.nammapustaka_library.data

import com.example.nammapustaka_library.model.Reservation
import com.example.nammapustaka_library.model.ReservationDao

class ReservationRepository(

    private val reservationDao:
    ReservationDao
) {

    suspend fun reserveBook(

        bookId: Int,

        bookName: String,

        studentName: String,

        studentId: String
    ) {

        val exists = reservationDao
            .reservationExists(

                bookId,

                studentId
            )

        if (exists == 0) {

            reservationDao.insertReservation(

                Reservation(

                    bookId = bookId,

                    bookName = bookName,

                    studentName = studentName,

                    studentId = studentId
                )
            )
        }
    }

    fun getReservationsForStudent(
        studentId: String
    ) = reservationDao
        .getReservationsForStudent(studentId)
}