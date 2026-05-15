package com.example.nammapustaka_library.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class Review(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val bookId: Int,

    val studentName: String,

    val rating: Int,

    val reviewText: String
)