package com.example.nammapustaka_library.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    val author: String,

    val category: String = "Story",

    val imageUri: String = "",

    val qrCode: String = "",

    val isIssued: Boolean = false,

    val issuedTo: String = "",

    val issuedStudentId: String = "",

    val issueDate: String = "",

    val returnDate: String = "",

    val overdueDays: Int = 0,

    val fine: Int = 0,

    val isReserved: Boolean = false,

    val reservedBy: String = ""


)