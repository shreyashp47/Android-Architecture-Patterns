package com.shreyash.cleanarchitecture.data.repository

import com.shreyash.cleanarchitecture.data.datasource.TaskDataSource
import com.shreyash.cleanarchitecture.domain.model.Task
import com.shreyash.cleanarchitecture.domain.repository.TaskRepository

class TaskRepositoryImpl(private val taskDataSource: TaskDataSource) : TaskRepository {
    override fun getTasks(): List<Task> {
        return taskDataSource.getTasks()
    }

    override fun addTask(task: Task) {
        taskDataSource.addTask(task)
    }

    override fun deleteTask(task: Task) {
        taskDataSource.deleteTask(task)
    }

    override fun toggleTaskComplete(task: Task): Task {
        val updatedTask = task.copy(isCompleted = !task.isCompleted)
        return taskDataSource.updateTask(updatedTask)
    }
}