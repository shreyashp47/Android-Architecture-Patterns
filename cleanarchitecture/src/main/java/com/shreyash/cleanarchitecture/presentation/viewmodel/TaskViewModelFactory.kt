package com.shreyash.cleanarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shreyash.cleanarchitecture.data.datasource.InMemoryTaskDataSource
import com.shreyash.cleanarchitecture.data.repository.TaskRepositoryImpl
import com.shreyash.cleanarchitecture.domain.usecase.AddTaskUseCase
import com.shreyash.cleanarchitecture.domain.usecase.DeleteTaskUseCase
import com.shreyash.cleanarchitecture.domain.usecase.GetTasksUseCase
import com.shreyash.cleanarchitecture.domain.usecase.ToggleTaskCompleteUseCase

class TaskViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            // Create data source
            val taskDataSource = InMemoryTaskDataSource()
            
            // Create repository
            val taskRepository = TaskRepositoryImpl(taskDataSource)
            
            // Create use cases
            val getTasksUseCase = GetTasksUseCase(taskRepository)
            val addTaskUseCase = AddTaskUseCase(taskRepository)
            val deleteTaskUseCase = DeleteTaskUseCase(taskRepository)
            val toggleTaskCompleteUseCase = ToggleTaskCompleteUseCase(taskRepository)
            
            // Create view model
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(
                getTasksUseCase,
                addTaskUseCase,
                deleteTaskUseCase,
                toggleTaskCompleteUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}