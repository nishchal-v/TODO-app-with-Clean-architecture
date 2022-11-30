package com.example.todo.presentation.ui.newtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.usecase.AddTask
import com.example.todo.presentation.utils.Event
import kotlinx.coroutines.launch

class NewTaskViewModel(
    private val mAddTask: AddTask
) : ViewModel() {

    private val mEmptyError = MutableLiveData<Event<EmptyError>>()
    val emptyError: LiveData<Event<EmptyError>> get() = mEmptyError

    fun addTask(title: String, description: String, onSuccess: (String) -> Unit) {
        if (title.isBlank()) {
            mEmptyError.value = Event(EmptyError.TITLE)
            return
        }
        if (description.isBlank()) {
            mEmptyError.value = Event(EmptyError.DESCRIPTION)
            return
        }
        viewModelScope.launch {
            mAddTask.invoke(title, description)
            onSuccess.invoke(title)
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