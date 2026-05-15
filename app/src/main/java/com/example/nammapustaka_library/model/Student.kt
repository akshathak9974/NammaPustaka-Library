package com.example.nammapustaka_library.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(

    @PrimaryKey
    val studentId: String,

    val studentName: String,

    val password: String,

    val studentClass: String,

    val pagesRead: Int = 0
)