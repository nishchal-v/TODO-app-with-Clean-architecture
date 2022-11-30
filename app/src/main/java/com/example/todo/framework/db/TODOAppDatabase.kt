package com.example.todo.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.framework.db.dao.TaskHistoryDao
import com.example.todo.framework.db.entity.TaskEntity

@Database(
    version = 1,
    entities = [
        TaskEntity::class
    ]
)
abstract class TODOAppDatabase: RoomDatabase()  {

    abstract fun taskHistoryDao(): TaskHistoryDao

    companion object {
        private var INSTANCE: TODOAppDatabase? = null
        private val mLock = Any()

        fun getInstance(context: Context): TODOAppDatabase =
            INSTANCE ?: synchronized(mLock) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TODOAppDatabase::class.java,
                    "com.example.todo"
                ).build().apply { INSTANCE = this }
            }
    }
}