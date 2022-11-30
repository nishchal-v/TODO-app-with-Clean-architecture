package com.example.todo.presentation.utils

import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.core.domain.CompletionStatus
import com.core.domain.Task
import com.example.todo.R
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("taskStatusColor")
fun View.setTaskStatusColor(task: Task) {
    background =
        AppCompatResources.getDrawable(
            context,
            when (task.completionStatus) {
                CompletionStatus.InComplete -> R.color.white
                else -> R.color.success_color
            }
        )
}

@BindingAdapter("taskCompletionDate")
fun TextView.setTaskStatusColor(task: Task) = with(task.completionStatus) {
    when (this) {
        is CompletionStatus.Complete -> {
            val dateFormatter =
                SimpleDateFormat("dd-MM-yyyy", resources.configuration.locales.get(0))
            text = dateFormatter.format(completionDate.time)
            visibility = View.VISIBLE
        }
        CompletionStatus.InComplete -> visibility = View.GONE
    }
}