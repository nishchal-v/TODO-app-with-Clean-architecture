package com.example.todo.presentation.utils

import androidx.lifecycle.Observer

class EventObserver<U, T: Event<U>>(private val mEventListener: (U) -> Unit): Observer<T> {

    override fun onChanged(t: T) {
        if (t.hasBeenHandled) return
        t.getContentIfNotHandled()?.let { mEventListener.invoke(it as U) }
    }
}