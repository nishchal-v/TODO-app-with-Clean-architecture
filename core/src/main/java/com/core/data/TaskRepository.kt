package com.core.data

import com.core.domain.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val mTaskDataSource: TaskDataSource) {

    suspend fun addTask(task: Task) = mTaskDataSource.addTask(task)

    suspend fun updateTask(task: Task) = mTaskDataSource.updateTask(task)

    suspend fun deleteTask(task: Task) = mTaskDataSource.deleteTask(task)

    suspend fun getTaskById(id: String): Task = mTaskDataSource.getTaskById(id)

    fun getTasksFlow(): Flow<List<Task>> = mTaskDataSource.getTasksFlow()

    fun getBinTasks(): Flow<List<Task>> = mTaskDataSource.getBinTasksAsFlow()
}