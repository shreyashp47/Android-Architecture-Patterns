package com.shreyash.cleanarchitecture.domain.repository

import com.shreyash.cleanarchitecture.domain.model.Task

interface TaskRepository {
    fun getTasks(): List<Task>
    fun addTask(task: Task)
    fun deleteTask(task: Task)
    fun toggleTaskComplete(task: Task): Task
}