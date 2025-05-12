package com.shreyash.cleanarchitecture.domain.usecase

import com.shreyash.cleanarchitecture.domain.model.Task
import com.shreyash.cleanarchitecture.domain.repository.TaskRepository

class ToggleTaskCompleteUseCase(private val taskRepository: TaskRepository) {
    operator fun invoke(task: Task): Task {
        return taskRepository.toggleTaskComplete(task)
    }
}