package com.example.nammapustaka_library.data

import com.example.nammapustaka_library.model.Student
import com.example.nammapustaka_library.model.StudentDao

class StudentRepository(
    private val dao: StudentDao
) {

    suspend fun registerStudent(
        student: Student
    ) {

        dao.insertStudent(student)
    }

    fun getStudents() =
        dao.getStudents()

    suspend fun loginStudent(
        id: String,
        password: String
    ): Student? {

        return dao.loginStudent(
            id,
            password
        )
    }
}