package com.example.todo.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.core.domain.CompletionStatus
import com.example.todo.framework.db.converter.CompletionStatusConverter

@Entity(tableName = "task_history")
@TypeConverters(
    value = [CompletionStatusConverter::class]
)
data class TaskEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: String = "0",
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "completionDate") private val mCompletionStatus: CompletionStatus?,
    @ColumnInfo(name = "isInTheBin") val isInTheBin: Boolean
) {
    val completionStatus: CompletionStatus
        get() = mCompletionStatus ?: CompletionStatus.InComplete
}