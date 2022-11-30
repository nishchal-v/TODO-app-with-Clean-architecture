package com.core.usecase

import com.core.data.TaskRepository
import com.core.domain.CompletionStatus
import com.core.domain.Task
import java.util.UUID

class AddTask(private val mTaskRepository: TaskRepository) {

    suspend fun invoke(title: String, description: String) {
        val newTask =
            Task(
                id = UUID.randomUUID().toString(),
                title = title,
                description = description,
                completionStatus = CompletionStatus.InComplete,
                isDeleted = false
            )
        mTaskRepository.addTask(newTask)
    }
}