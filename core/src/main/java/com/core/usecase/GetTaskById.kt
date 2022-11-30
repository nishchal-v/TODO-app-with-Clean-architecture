package com.core.usecase

import com.core.data.TaskRepository
import com.core.domain.Task

class GetTaskById(private val mTaskRepository: TaskRepository) {

    suspend fun invoke(id: String): Task = mTaskRepository.getTaskById(id)
}