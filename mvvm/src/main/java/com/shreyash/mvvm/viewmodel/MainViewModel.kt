package com.shreyash.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shreyash.mvvm.model.Task
import com.shreyash.mvvm.model.TaskRepository

class MainViewModel : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        loadTasks()
    }

    fun addTask(title: String, description: String) {
        if (title.isBlank() || description.isBlank()) {
            _error.value = "Please enter both title and description"
            return
        }
        val task = Task(
            id = System.currentTimeMillis(),
            title = title,
            description = description
        )
        TaskRepository.addTask(task)
        loadTasks()
    }

    fun deleteTask(task: Task) {
        TaskRepository.deleteTask(task)
        loadTasks()
    }

    fun toggleTaskComplete(task: Task) {
        TaskRepository.toggleTaskComplete(task)
        loadTasks()
    }

    private fun loadTasks() {
        _tasks.value = TaskRepository.getTasks()
    }
}
