package com.shreyash.cleanarchitecture.domain.usecase

import com.shreyash.cleanarchitecture.domain.model.Task
import com.shreyash.cleanarchitecture.domain.repository.TaskRepository

class DeleteTaskUseCase(private val taskRepository: TaskRepository) {
    operator fun invoke(task: Task) {
        taskRepository.deleteTask(task)
    }
}