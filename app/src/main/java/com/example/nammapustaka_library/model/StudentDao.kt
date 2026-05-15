package com.example.nammapustaka_library.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {

    @Insert
    suspend fun insertStudent(student: Student)

    @Query("SELECT * FROM students")
    fun getStudents(): Flow<List<Student>>

    @Query(
        """
        SELECT * FROM students
        WHERE studentId = :id
        AND password = :password
        LIMIT 1
        """
    )
    suspend fun loginStudent(
        id: String,
        password: String
    ): Student?
}