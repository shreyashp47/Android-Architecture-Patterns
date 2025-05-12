package com.shreyash.cleanarchitecture.domain.usecase

import com.shreyash.cleanarchitecture.domain.model.Task
import com.shreyash.cleanarchitecture.domain.repository.TaskRepository

class AddTaskUseCase(private val taskRepository: TaskRepository) {
    operator fun invoke(title: String, description: String) {
        val newTask = Task(
            id = System.currentTimeMillis(),
            title = title,
            description = description
        )
        taskRepository.addTask(newTask)
    }
}