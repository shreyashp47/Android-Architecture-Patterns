package com.shreyash.cleanarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shreyash.cleanarchitecture.domain.model.Task
import com.shreyash.cleanarchitecture.domain.usecase.AddTaskUseCase
import com.shreyash.cleanarchitecture.domain.usecase.DeleteTaskUseCase
import com.shreyash.cleanarchitecture.domain.usecase.GetTasksUseCase
import com.shreyash.cleanarchitecture.domain.usecase.ToggleTaskCompleteUseCase
import com.shreyash.cleanarchitecture.presentation.state.TaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val toggleTaskCompleteUseCase: ToggleTaskCompleteUseCase
) : ViewModel() {

    private val _taskState = MutableStateFlow<TaskState>(TaskState.Loading)
    val taskState: StateFlow<TaskState> = _taskState

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            try {
                _taskState.value = TaskState.Loading
                val tasks = getTasksUseCase()
                _taskState.value = TaskState.Success(tasks)
            } catch (e: Exception) {
                _taskState.value = TaskState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            try {
                addTaskUseCase(title, description)
                loadTasks()
            } catch (e: Exception) {
                _taskState.value = TaskState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                deleteTaskUseCase(task)
                loadTasks()
            } catch (e: Exception) {
                _taskState.value = TaskState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun toggleTaskComplete(task: Task) {
        viewModelScope.launch {
            try {
                toggleTaskCompleteUseCase(task)
                loadTasks()
            } catch (e: Exception) {
                _taskState.value = TaskState.Error(e.message ?: "Unknown error")
            }
        }
    }
}