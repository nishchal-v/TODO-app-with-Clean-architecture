package com.core.usecase

import com.core.data.TaskRepository
import com.core.domain.Task
import kotlinx.coroutines.flow.Flow

class GetBinTasks(private val mTaskRepository: TaskRepository) {

    fun invoke(): Flow<List<Task>> = mTaskRepository.getBinTasks()
}