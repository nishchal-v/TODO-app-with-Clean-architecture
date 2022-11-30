package com.core.data

import com.core.domain.Task
import kotlinx.coroutines.flow.Flow

interface TaskDataSource {

    suspend fun addTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun getTaskById(id: String): Task

    fun getTasksFlow(): Flow<List<Task>>

    fun getBinTasksAsFlow(): Flow<List<Task>>
}