package com.shreyash.cleanarchitecture.presentation.state

import com.shreyash.cleanarchitecture.domain.model.Task

sealed class TaskState {
    object Loading : TaskState()
    data class Success(val tasks: List<Task>) : TaskState()
    data class Error(val message: String) : TaskState()
}