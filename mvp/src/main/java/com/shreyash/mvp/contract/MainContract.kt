package com.shreyash.mvp.contract

import com.shreyash.mvp.model.Task

interface MainContract {

    interface View {
        fun showTasks(tasks: List<Task>)
        fun showError(message: String)
        fun clearInputFields()
    }

    interface Presenter {
        fun addTask(title: String, description: String)
        fun deleteTask(task: Task)
        fun toggleTaskComplete(task: Task)
        fun loadTasks()
    }
}