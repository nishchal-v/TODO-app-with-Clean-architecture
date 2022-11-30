package com.core.domain

data class Task(
    val id: String,
    val title: String = "",
    val description: String = "",
    val completionStatus: CompletionStatus,
    val isDeleted: Boolean
)