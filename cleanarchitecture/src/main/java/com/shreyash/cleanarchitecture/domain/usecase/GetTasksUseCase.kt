package com.shreyash.cleanarchitecture.domain.usecase

import com.shreyash.cleanarchitecture.domain.model.Task
import com.shreyash.cleanarchitecture.domain.repository.TaskRepository

class GetTasksUseCase(private val taskRepository: TaskRepository) {
    operator fun invoke(): List<Task> {
        return taskRepository.getTasks()
    }
}