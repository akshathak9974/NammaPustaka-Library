package com.example.nammapustaka_library.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservations")
data class Reservation(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val bookId: Int,

    val bookName: String,

    val studentName: String,

    val studentId: String
)