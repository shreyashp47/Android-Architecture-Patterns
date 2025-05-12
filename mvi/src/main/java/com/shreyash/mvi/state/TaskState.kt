package com.shreyash.mvi.state

import com.shreyash.mvi.model.Task

//represents the entire UI state
sealed class TaskState {
    object Idle : TaskState()
    object Loading : TaskState()
    data class Tasks(val taskList: List<Task>) : TaskState()
    data class Error(val message: String) : TaskState()
}
