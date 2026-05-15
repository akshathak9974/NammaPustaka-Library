package com.example.nammapustaka_library.data

import android.content.Context
import androidx.room.Room
import com.example.nammapustaka_library.model.BookDatabase

object DatabaseProvider {

    private var INSTANCE: BookDatabase? = null

    fun getDatabase(context: Context): BookDatabase {

        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                BookDatabase::class.java,
                "book_database"
            )
                .fallbackToDestructiveMigration()
                .build()

            INSTANCE = instance
            instance
        }
    }
}