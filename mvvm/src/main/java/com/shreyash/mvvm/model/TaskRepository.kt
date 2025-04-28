package com.shreyash.mvvm.model


object TaskRepository {

    private val tasks = mutableListOf<Task>()

    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun deleteTask(task: Task) {
        tasks.remove(task)
    }

    fun toggleTaskComplete(task: Task) {
        task.isCompleted = !task.isCompleted
    }

    fun getTasks(): List<Task> {
        return tasks
    }
}
