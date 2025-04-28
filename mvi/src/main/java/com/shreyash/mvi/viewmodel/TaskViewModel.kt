package com.shreyash.mvi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shreyash.mvi.intent.TaskIntent
import com.shreyash.mvi.model.Task
import com.shreyash.mvi.model.TaskRepository
import com.shreyash.mvi.state.TaskState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    val intentChannel = Channel<TaskIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<TaskState>(TaskState.Idle)
    val state: StateFlow<TaskState> get() = _state

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is TaskIntent.AddTask -> addTask(intent.title, intent.description)
                    is TaskIntent.DeleteTask -> deleteTask(intent.task)
                    is TaskIntent.ToggleTaskComplete -> toggleTaskComplete(intent.task)
                    is TaskIntent.LoadTasks -> loadTasks()
                }
            }
        }
    }

    private fun loadTasks() {
        _state.value = TaskState.Tasks(TaskRepository.getTasks())
    }

    private fun addTask(title: String, description: String) {
        if (title.isBlank() || description.isBlank()) {
            _state.value = TaskState.Error("Title and description cannot be empty")
            return
        }
        TaskRepository.addTask(Task(System.currentTimeMillis(), title, description))
        loadTasks()
    }

    private fun deleteTask(task: Task) {
        TaskRepository.deleteTask(task)
        loadTasks()
    }

    private fun toggleTaskComplete(task: Task) {
        TaskRepository.toggleTaskComplete(task)
        loadTasks()
    }
}