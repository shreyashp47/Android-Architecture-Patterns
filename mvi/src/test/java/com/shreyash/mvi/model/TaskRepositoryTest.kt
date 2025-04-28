package com.shreyash.mvi.model

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TaskRepositoryTest {

    @Before
    fun setup() {
        // Clear all tasks before each test to ensure a clean state
        val tasks = TaskRepository.getTasks().toList()
        tasks.forEach { TaskRepository.deleteTask(it) }
        
        // After clearing, there should be 1 demo task automatically added
        assertEquals(1, TaskRepository.getTasks().size)
    }

    @Test
    fun `addTask should add a task to the repository`() {
        // Given
        val task = Task(2, "Test Task", "Test Description")
        
        // When
        TaskRepository.addTask(task)
        
        // Then
        val tasks = TaskRepository.getTasks()
        assertEquals(2, tasks.size) // 1 demo task + 1 new task
        assertEquals(task, tasks[1]) // New task should be at index 1
    }

    @Test
    fun `deleteTask should remove a task from the repository`() {
        // Given - get the demo task
        val demoTask = TaskRepository.getTasks()[0]
        
        // When
        TaskRepository.deleteTask(demoTask)
        
        // Then - a new demo task should be automatically added
        val tasks = TaskRepository.getTasks()
        assertEquals(1, tasks.size)
        // The new demo task should have the same properties except for the ID
        assertEquals("Welcome to MVI Task App", tasks[0].title)
    }

    @Test
    fun `toggleTaskComplete should toggle the completion status of a task`() {
        // Given - use the demo task
        val demoTask = TaskRepository.getTasks()[0]
        
        // When
        TaskRepository.toggleTaskComplete(demoTask)
        
        // Then
        val tasks = TaskRepository.getTasks()
        assertEquals(1, tasks.size)
        assertTrue(tasks[0].isCompleted)
        
        // Toggle again
        TaskRepository.toggleTaskComplete(tasks[0])
        
        // Verify toggled back
        val updatedTasks = TaskRepository.getTasks()
        assertFalse(updatedTasks[0].isCompleted)
    }

    @Test
    fun `toggleTaskComplete should do nothing if task not found`() {
        // Given
        val nonExistingTask = Task(999, "Non-existing Task", "Description")
        
        // When
        TaskRepository.toggleTaskComplete(nonExistingTask)
        
        // Then - no exception should be thrown and demo task should remain unchanged
        val tasks = TaskRepository.getTasks()
        assertEquals(1, tasks.size)
        assertEquals("Welcome to MVI Task App", tasks[0].title)
        assertFalse(tasks[0].isCompleted)
    }

    @Test
    fun `getTasks should return a copy of the tasks list`() {
        // Given
        val task1 = Task(2, "Task 1", "Description 1")
        val task2 = Task(3, "Task 2", "Description 2")
        TaskRepository.addTask(task1)
        TaskRepository.addTask(task2)
        
        // When
        val tasks = TaskRepository.getTasks()
        
        // Then
        assertEquals(3, tasks.size) // 1 demo task + 2 new tasks
        assertTrue(tasks.contains(task1))
        assertTrue(tasks.contains(task2))
        
        // Verify that the returned list is a copy by modifying the original list
        // and checking that the previously obtained list is not affected
        TaskRepository.addTask(Task(4, "Task 3", "Description 3"))
        
        // The previously obtained list should still have only 3 items
        assertEquals(3, tasks.size)
        
        // But a new call to getTasks() should return 4 items
        val updatedTasks = TaskRepository.getTasks()
        assertEquals(4, updatedTasks.size)
    }

    @Test
    fun `multiple tasks can be added and retrieved correctly`() {
        // Given
        val task1 = Task(2, "Task 1", "Description 1")
        val task2 = Task(3, "Task 2", "Description 2")
        val task3 = Task(4, "Task 3", "Description 3")
        
        // When
        TaskRepository.addTask(task1)
        TaskRepository.addTask(task2)
        TaskRepository.addTask(task3)
        
        // Then
        val tasks = TaskRepository.getTasks()
        assertEquals(4, tasks.size) // 1 demo task + 3 new tasks
        // Demo task is at index 0
        assertEquals(task1, tasks[1])
        assertEquals(task2, tasks[2])
        assertEquals(task3, tasks[3])
    }
}