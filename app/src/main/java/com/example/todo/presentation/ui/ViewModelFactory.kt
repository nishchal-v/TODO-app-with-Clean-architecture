package com.example.todo.presentation.ui

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.core.data.TaskRepository
import com.core.usecase.*
import com.example.todo.framework.data.RoomTaskDataSource
import com.example.todo.framework.db.TODOAppDatabase
import com.example.todo.presentation.ui.bin.BinViewModel
import com.example.todo.presentation.ui.details.TaskDetailsViewModel
import com.example.todo.presentation.ui.tasks.TasksViewModel
import com.example.todo.presentation.ui.newtask.NewTaskViewModel

class ViewModelFactory(
    private val mApplication: Application,
    private val mSharedPreferences: SharedPreferences
) : ViewModelProvider.Factory {

    private val mDatabase: TODOAppDatabase by lazy { TODOAppDatabase.getInstance(mApplication) }
    private val mTaskRepository: TaskRepository by lazy {
        TaskRepository(RoomTaskDataSource(mDatabase.taskHistoryDao()))
    }
    private val mGetTasks: GetTasks by lazy { GetTasks(mTaskRepository) }
    private val mGetTaskById: GetTaskById by lazy { GetTaskById(mTaskRepository) }
    private val mAddTask: AddTask by lazy { AddTask(mTaskRepository) }
    private val mUpdateTask: UpdateTask by lazy { UpdateTask(mTaskRepository) }
    private val mMoveToBin: MoveToBin by lazy { MoveToBin(mTaskRepository) }
    private val mRestoreFromBin: RestoreFromBin by lazy { RestoreFromBin(mTaskRepository) }
    private val mCompleteTask: CompleteTask by lazy { CompleteTask(mTaskRepository) }
    private val mGetBinTasks: GetBinTasks by lazy { GetBinTasks(mTaskRepository) }
    private val mDeleteTask: DeleteTask by lazy { DeleteTask(mTaskRepository) }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            // region B
            isAssignableFrom(BinViewModel::class.java) -> {
                BinViewModel(mGetBinTasks, mRestoreFromBin, mDeleteTask)
            }
            // region end

            // region N
            isAssignableFrom(NewTaskViewModel::class.java) -> {
                NewTaskViewModel(mAddTask)
            }
            // region end

            // region T
            isAssignableFrom(TaskDetailsViewModel::class.java) -> {
                TaskDetailsViewModel(mGetTaskById, mUpdateTask)
            }
            isAssignableFrom(TasksViewModel::class.java) -> {
                TasksViewModel(mGetTasks, mCompleteTask, mMoveToBin)
            }
            // region end
            else -> {
                throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
            }
        }
    } as T

    companion object {

        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                ViewModelFactory(
                    application,
                    application.getSharedPreferences(
                        "com.example.todo", Context.MODE_PRIVATE
                    )
                ).apply { INSTANCE = this }
            }
    }
}