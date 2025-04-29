package com.shreyash.mvi.intent

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shreyash.mvi.model.Task
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskIntentTest {

    @Test
    fun testAddTaskIntent() {
        // Create an AddTask intent
        val title = "Test Title"
        val description = "Test Description"
        val intent = TaskIntent.AddTask(title, description)
        
        // Verify the properties
        assertEquals(title, intent.title)
        assertEquals(description, intent.description)
    }
    
    @Test
    fun testDeleteTaskIntent() {
        // Create a task
        val task = Task(1, "Test Task", "Test Description")
        
        // Create a DeleteTask intent
        val intent = TaskIntent.DeleteTask(task)
        
        // Verify the property
        assertEquals(task, intent.task)
    }
    
    @Test
    fun testToggleTaskCompleteIntent() {
        // Create a task
        val task = Task(1, "Test Task", "Test Description")
        
        // Create a ToggleTaskComplete intent
        val intent = TaskIntent.ToggleTaskComplete(task)
        
        // Verify the property
        assertEquals(task, intent.task)
    }
    
    @Test
    fun testLoadTasksIntent() {
        // Create a LoadTasks intent
        val intent = TaskIntent.LoadTasks
        
        // Verify it's the correct type
        assert(intent is TaskIntent.LoadTasks)
    }
}