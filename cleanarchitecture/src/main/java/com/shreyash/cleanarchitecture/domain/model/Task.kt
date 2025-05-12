package com.shreyash.cleanarchitecture.domain.model

data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false
)