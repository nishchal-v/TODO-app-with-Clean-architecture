package com.example.todo.framework.db.converter

import androidx.room.TypeConverter
import com.core.domain.CompletionStatus
import java.util.Date

class CompletionStatusConverter {

    @TypeConverter
    fun CompletionStatus.toTime(): Long? =
        (this as? CompletionStatus.Complete)?.completionDate?.time

    @TypeConverter
    fun Long?.toTime(): CompletionStatus =
        if (this == null)
            CompletionStatus.InComplete
        else
            CompletionStatus.Complete(Date(this))
}