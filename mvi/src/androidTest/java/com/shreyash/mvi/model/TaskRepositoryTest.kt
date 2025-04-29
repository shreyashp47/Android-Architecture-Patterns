package com.shreyash.mvi.model

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskRepositoryTest {

    @Test
    fun testInitialState() {
        // Get the initial tasks
        val tasks = TaskRepository.getTasks()
        
        // Verify there's at least one task (the demo task)
        assertTrue(tasks.isNotEmpty())
        
        // Verify the demo task content
        val demoTask = tasks.find { it.title.contains("Welcome to MVI Task App") }
        assertNotNull(demoTask)
        assertFalse(demoTask!!.isCompleted)
    }
    
    @Test
    fun testAddTask() {
        // Get the initial count
        val initialCount = TaskRepository.getTasks().size
        
        // Create a new task
        val newTask = Task(
            id = System.currentTimeMillis(),
            title = "Test Task",
            description = "Test Description"
        )
        
        // Add the task
        TaskRepository.addTask(newTask)
        
        // Get the updated tasks
        val updatedTasks = TaskRepository.getTasks()
        
        // Verify the count increased
        assertEquals(initialCount + 1, updatedTasks.size)
        
        // Verify the new task is in the list
        val addedTask = updatedTasks.find { it.id == newTask.id }
        assertNotNull(addedTask)
        assertEquals(newTask.title, addedTask!!.title)
        assertEquals(newTask.description, addedTask.description)
        assertEquals(newTask.isCompleted, addedTask.isCompleted)
    }
    
    @Test
    fun testDeleteTask() {
        // Create and add a task
        val taskToDelete = Task(
            id = System.currentTimeMillis(),
            title = "Task to Delete",
            description = "This task will be deleted"
        )
        TaskRepository.addTask(taskToDelete)
        
        // Verify the task was added
        val tasksAfterAdd = TaskRepository.getTasks()
        val addedTask = tasksAfterAdd.find { it.id == taskToDelete.id }
        assertNotNull(addedTask)
        
        // Delete the task
        TaskRepository.deleteTask(taskToDelete)
        
        // Verify the task was deleted
        val tasksAfterDelete = TaskRepository.getTasks()
        val deletedTask = tasksAfterDelete.find { it.id == taskToDelete.id }
        assertNull(deletedTask)
    }
    
    @Test
    fun testToggleTaskComplete() {
        // Create and add a task
        val task = Task(
            id = System.currentTimeMillis(),
            title = "Task to Toggle",
            description = "This task's completion status will be toggled",
            isCompleted = false
        )
        TaskRepository.addTask(task)
        
        // Verify the task was added with isCompleted = false
        val tasksAfterAdd = TaskRepository.getTasks()
        val addedTask = tasksAfterAdd.find { it.id == task.id }
        assertNotNull(addedTask)
        assertFalse(addedTask!!.isCompleted)
        
        // Toggle the task
        TaskRepository.toggleTaskComplete(task)
        
        // Verify the task was toggled to isCompleted = true
        val tasksAfterToggle = TaskRepository.getTasks()
        val toggledTask = tasksAfterToggle.find { it.id == task.id }
        assertNotNull(toggledTask)
        assertTrue(toggledTask!!.isCompleted)
        
        // Toggle it back
        TaskRepository.toggleTaskComplete(toggledTask)
        
        // Verify the task was toggled back to isCompleted = false
        val tasksAfterSecondToggle = TaskRepository.getTasks()
        val toggledBackTask = tasksAfterSecondToggle.find { it.id == task.id }
        assertNotNull(toggledBackTask)
        assertFalse(toggledBackTask!!.isCompleted)
    }
    
    @Test
    fun testGetTasks() {
        // Clear existing tasks by getting a reference to them and removing them
        // Note: This is a bit of a hack since we don't have a clear method in the repository
        val initialTasks = TaskRepository.getTasks().toMutableList()
        initialTasks.forEach { TaskRepository.deleteTask(it) }
        
        // Verify the repository is empty
        assertTrue(TaskRepository.getTasks().isEmpty())
        
        // Add some tasks
        val task1 = Task(1, "Task 1", "Description 1")
        val task2 = Task(2, "Task 2", "Description 2")
        val task3 = Task(3, "Task 3", "Description 3")
        
        TaskRepository.addTask(task1)
        TaskRepository.addTask(task2)
        TaskRepository.addTask(task3)
        
        // Get the tasks
        val tasks = TaskRepository.getTasks()
        
        // Verify the count
        assertEquals(3, tasks.size)
        
        // Verify all tasks are in the list
        assertNotNull(tasks.find { it.id == task1.id })
        assertNotNull(tasks.find { it.id == task2.id })
        assertNotNull(tasks.find { it.id == task3.id })
    }
}