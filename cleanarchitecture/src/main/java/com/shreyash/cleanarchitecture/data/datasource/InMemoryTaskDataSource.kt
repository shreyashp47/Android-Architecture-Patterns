package com.shreyash.cleanarchitecture.data.datasource

import com.shreyash.cleanarchitecture.domain.model.Task

class InMemoryTaskDataSource : TaskDataSource {
    private val tasks = mutableListOf<Task>()

    init {
        // Add a demo task when the data source is initialized
        if (tasks.isEmpty()) {
            tasks.add(
                Task(
                    id = 1,
                    title = "Welcome to Clean Architecture Task App",
                    description = "This is a demo task. You can add more tasks using the form below.",
                    isCompleted = false
                )
            )
        }
    }

    override fun getTasks(): List<Task> {
        return tasks.toList()
    }

    override fun addTask(task: Task) {
        tasks.add(task)
    }

    override fun deleteTask(task: Task) {
        tasks.remove(task)
    }

    override fun updateTask(task: Task): Task {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            tasks[index] = task
            return task
        }
        return task
    }
}