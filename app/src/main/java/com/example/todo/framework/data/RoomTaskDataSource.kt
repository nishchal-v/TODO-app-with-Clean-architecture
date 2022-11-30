package com.example.todo.framework.data

import com.core.data.TaskDataSource
import com.core.domain.Task
import com.example.todo.framework.db.dao.TaskHistoryDao
import com.example.todo.framework.db.entity.TaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomTaskDataSource(
    private val mTaskHistoryDao: TaskHistoryDao
) : TaskDataSource {

    override suspend fun addTask(task: Task) = withContext(Dispatchers.IO) {
        mTaskHistoryDao.addTask(task.toTaskEntity())
    }

    override suspend fun updateTask(task: Task) = withContext(Dispatchers.IO) {
        mTaskHistoryDao.updateTask(task.toTaskEntity())
    }

    override suspend fun deleteTask(task: Task) = withContext(Dispatchers.IO) {
        mTaskHistoryDao.deleteTask(task.toTaskEntity())
    }

    override suspend fun getTaskById(id: String): Task = withContext(Dispatchers.IO) {
        mTaskHistoryDao.getTaskById(id).toTask()
    }

    override fun getTasksFlow(): Flow<List<Task>> =
        mTaskHistoryDao.getTasksAsFlow()
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
            .map { entities -> entities.map { it.toTask() } }

    override fun getBinTasksAsFlow(): Flow<List<Task>> =
        mTaskHistoryDao.getBinItemsAsFlow()
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
            .map { entities -> entities.map { it.toTask() } }

    private fun Task.toTaskEntity(): TaskEntity =
        TaskEntity(
            id = id,
            title = title,
            description = description,
            mCompletionStatus = completionStatus,
            isInTheBin = isDeleted
        )

    private fun TaskEntity.toTask(): Task =
        Task(
            id = id,
            title = title,
            description = description,
            completionStatus = completionStatus,
            isDeleted = isInTheBin
        )
}