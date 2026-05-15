package com.example.nammapustaka_library.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {

    @Insert
    suspend fun insertReservation(
        reservation: Reservation
    )

    @Query(
        """
        SELECT * FROM reservations
        WHERE studentId = :studentId
        """
    )
    fun getReservationsForStudent(
        studentId: String
    ): Flow<List<Reservation>>

    @Query(
        """
        SELECT COUNT(*) FROM reservations
        WHERE bookId = :bookId
        AND studentId = :studentId
        """
    )
    suspend fun reservationExists(

        bookId: Int,

        studentId: String
    ): Int
}