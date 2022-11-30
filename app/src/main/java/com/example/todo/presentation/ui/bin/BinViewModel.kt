package com.example.todo.presentation.ui.bin

import androidx.lifecycle.*
import com.core.domain.Task
import com.core.usecase.DeleteTask
import com.core.usecase.GetBinTasks
import com.core.usecase.RestoreFromBin
import com.example.todo.presentation.utils.Event
import kotlinx.coroutines.launch

class BinViewModel(
    getBinTasks: GetBinTasks,
    private val mRestoreFromBin: RestoreFromBin,
    private val mDeleteTask: DeleteTask
) : ViewModel() {

    val binTasks: LiveData<List<Task>> =
        getBinTasks.invoke().asLiveData()

    private val mTaskDeleted = MutableLiveData<Event<Task>>()
    val taskDeleted: LiveData<Event<Task>> get() = mTaskDeleted

    fun restore(task: Task, onSuccess: (Task) -> Unit) {
        viewModelScope.launch {
            mRestoreFromBin.invoke(task)
            onSuccess.invoke(task)
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch {
            mDeleteTask.invoke(task)
            mTaskDeleted.value = Event(task)
        }
    }
}