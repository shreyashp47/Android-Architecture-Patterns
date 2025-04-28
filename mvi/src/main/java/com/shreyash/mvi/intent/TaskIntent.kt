package com.shreyash.mvi.intent

import com.shreyash.mvi.model.Task


sealed class TaskIntent {
    data class AddTask(val title: String, val description: String) : TaskIntent()
    data class DeleteTask(val task: Task) : TaskIntent()
    data class ToggleTaskComplete(val task: Task) : TaskIntent()
    object LoadTasks : TaskIntent()
}
