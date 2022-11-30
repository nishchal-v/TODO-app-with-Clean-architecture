package com.example.todo.presentation.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T>LiveData<Event<T>>.observeEvent(
    lifecycleOwner: LifecycleOwner,
    observer: Observer<T>) {
    observe(lifecycleOwner, EventObserver {
        observer.onChanged(it)
    })
}