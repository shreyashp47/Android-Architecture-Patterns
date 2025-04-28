package com.shreyash.mvp.presenter

import com.shreyash.mvp.model.Task
import com.shreyash.mvp.model.TaskRepository
import com.shreyash.mvp.contract.MainContract


//handles ALL logic.
class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

    override fun addTask(title: String, description: String) {
        if (title.isBlank() || description.isBlank()) {
            view.showError("Please enter both title and description")
            return
        }
        val task = Task(
            id = System.currentTimeMillis(),
            title = title,
            description = description
        )
        TaskRepository.addTask(task)
        view.clearInputFields()
        loadTasks()
    }

    override fun deleteTask(task: Task) {
        TaskRepository.deleteTask(task)
        loadTasks()
    }

    override fun toggleTaskComplete(task: Task) {
        TaskRepository.toggleTaskComplete(task)
        loadTasks()
    }

    override fun loadTasks() {
        view.showTasks(TaskRepository.getTasks())
    }
}
