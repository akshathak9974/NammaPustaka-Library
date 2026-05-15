package com.example.nammapustaka_library.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nammapustaka_library.model.Student
import com.example.nammapustaka_library.model.StudentDao
import com.example.nammapustaka_library.model.Reservation
import com.example.nammapustaka_library.model.ReservationDao

@Database(
    entities = [
        Book::class,
        Student::class,
        Review::class,
        Reservation::class
    ],
    version = 11,
    exportSchema = false
)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
    abstract fun studentDao(): StudentDao
    abstract fun reviewDao(): ReviewDao
    abstract fun reservationDao():
            ReservationDao

}