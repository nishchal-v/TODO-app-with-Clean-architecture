package com.core.usecase

import com.core.data.TaskRepository
import com.core.domain.CompletionStatus
import com.core.domain.Task
import java.util.*

class CompleteTask(private val mTaskRepository: TaskRepository) {

    suspend fun invoke(task: Task) {
        mTaskRepository.updateTask(task.copy(completionStatus = CompletionStatus.Complete(Date())))
    }
}