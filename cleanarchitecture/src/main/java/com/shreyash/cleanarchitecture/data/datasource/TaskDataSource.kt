package com.shreyash.cleanarchitecture.data.datasource

import com.shreyash.cleanarchitecture.domain.model.Task

interface TaskDataSource {
    fun getTasks(): List<Task>
    fun addTask(task: Task)
    fun deleteTask(task: Task)
    fun updateTask(task: Task): Task
}