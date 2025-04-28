package com.shreyash.mvc.model


data class Task(
    val id: Long,
    val title: String,
    val description: String,
    var isCompleted: Boolean = false
)