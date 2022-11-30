package com.core.usecase

import com.core.data.TaskRepository
import com.core.domain.Task

class DeleteTask(private val mTaskRepository: TaskRepository) {

    suspend fun invoke(task: Task) = mTaskRepository.deleteTask(task)
}