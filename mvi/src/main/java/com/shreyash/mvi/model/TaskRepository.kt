package com.shreyash.mvi.model

object TaskRepository {

    private val tasks = mutableListOf<Task>()

    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun deleteTask(task: Task) {
        tasks.remove(task)
    }

    fun toggleTaskComplete(task: Task) {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            tasks[index] = updatedTask
        }
    }


    fun getTasks(): List<Task> {
        return tasks.toList() // important change
    }
}
