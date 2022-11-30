package com.example.todo.presentation.alert

import android.view.View
import com.google.android.material.snackbar.Snackbar

class Alerter(private val view: View) {

    fun showWarning(
        title: String,
        message: String
    ) {
        showSnakeBar(title, message)
    }

    fun showError(
        title: String,
        message: String
    ) {
        showSnakeBar(title, message)
    }

    fun showSuccess(
        title: String,
        message: String
    ) {
        showSnakeBar(title, message)
    }

    fun show(
        title: String,
        message: String
    ) {
        showSnakeBar(title, message)
    }

    private fun showSnakeBar(
        title: String,
        message: String
    ) {
        Snackbar.make(view, "$title $message", Snackbar.ANIMATION_MODE_SLIDE).show()
    }
}