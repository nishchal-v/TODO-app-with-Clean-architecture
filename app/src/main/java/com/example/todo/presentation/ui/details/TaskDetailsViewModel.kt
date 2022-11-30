package com.example.todo.presentation.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.Task
import com.core.usecase.GetTaskById
import com.core.usecase.UpdateTask
import com.example.todo.presentation.utils.Event
import kotlinx.coroutines.launch

class TaskDetailsViewModel(
    private val mGetTaskById: GetTaskById,
    private val mUpdateTask: UpdateTask
) : ViewModel() {

    private val mCurrentTask = MutableLiveData<Event<Task>>()
    val currentTask: LiveData<Event<Task>> get() = mCurrentTask

    private val mEmptyError = MutableLiveData<Event<EmptyError>>()
    val emptyError: LiveData<Event<EmptyError>> get() = mEmptyError

    fun setCurrentTask(taskId: String) {
        viewModelScope.launch {
            mCurrentTask.value = Event(mGetTaskById.invoke(taskId))
        }
    }

    fun updateTask(title: String, description: String, onSuccess: (String) -> Unit) {
        if (title.isBlank()) {
            mEmptyError.value = Event(EmptyError.TITLE)
            return
        }
        if (description.isBlank()) {
            mEmptyError.value = Event(EmptyError.DESCRIPTION)
            return
        }
        viewModelScope.launch {
            mCurrentTask.value?.peekContent()?.let {
                mUpdateTask.invoke(it.copy(title = title, description = description))
                onSuccess.invoke(title)
            }
        }
    }

    enum class EmptyError {
        TITLE,
        DESCRIPTION;

        override fun toString(): String =
            when (this) {
                TITLE -> "Title"
                DESCRIPTION -> "Description"
            }
    }
}