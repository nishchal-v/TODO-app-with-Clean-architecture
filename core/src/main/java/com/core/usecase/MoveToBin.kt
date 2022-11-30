package com.core.usecase

import com.core.data.TaskRepository
import com.core.domain.Task

class MoveToBin(private val mTaskRepository: TaskRepository) {

    suspend fun invoke(task: Task) = mTaskRepository.updateTask(task.copy(isDeleted = true))
}