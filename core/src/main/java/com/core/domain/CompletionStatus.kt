package com.core.domain

import java.util.Date

sealed class CompletionStatus {

    data class Complete(val completionDate: Date): CompletionStatus()

    object InComplete: CompletionStatus()
}