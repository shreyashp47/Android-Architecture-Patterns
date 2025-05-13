package com.shreyash.mvi.model

import org.junit.Assert.*
import org.junit.Test

class TaskTest {

    @Test
    fun `create task with default values`() {
        // When
        val task = Task(1, "Test Title", "Test Description")
        
        // Then
        assertEquals(1L, task.id)
        assertEquals("Test Title", task.title)
        assertEquals("Test Description", task.description)
        assertFalse(task.isCompleted)
    }
    
    @Test
    fun `create task with custom values`() {
        // When
        val task = Task(
            id = 42L,
            title = "Custom Task",
            description = "Custom Description",
            isCompleted = true
        )
        
        // Then
        assertEquals(42L, task.id)
        assertEquals("Custom Task", task.title)
        assertEquals("Custom Description", task.description)
        assertTrue(task.isCompleted)
    }
    
    @Test
    fun `toggle task completion status`() {
        // Given
        val task = Task(1, "Test Task", "Test Description")
        assertFalse(task.isCompleted)
        
        // When
        task.isCompleted = true
        
        // Then
        assertTrue(task.isCompleted)
        
        // When toggled again
        task.isCompleted = false
        
        // Then
        assertFalse(task.isCompleted)
    }
    
    @Test
    fun `copy task with modified properties`() {
        // Given
        val originalTask = Task(1, "Original Title", "Original Description")
        
        // When
        val copiedTask = originalTask.copy(
            title = "Modified Title",
            description = "Modified Description",
            isCompleted = true
        )
        
        // Then
        // Original task should remain unchanged
        assertEquals(1L, originalTask.id)
        assertEquals("Original Title", originalTask.title)
        assertEquals("Original Description", originalTask.description)
        assertFalse(originalTask.isCompleted)
        
        // Copied task should have the modified properties
        assertEquals(1L, copiedTask.id) // ID remains the same
        assertEquals("Modified Title", copiedTask.title)
        assertEquals("Modified Description", copiedTask.description)
        assertTrue(copiedTask.isCompleted)
    }
    
    @Test
    fun `compare equal tasks`() {
        // Given
        val task1 = Task(1, "Test Task", "Test Description")
        val task2 = Task(1, "Test Task", "Test Description")
        
        // Then
        assertEquals(task1, task2)
        assertEquals(task1.hashCode(), task2.hashCode())
        
        // When
        val task3 = task1.copy()
        
        // Then
        assertEquals(task1, task3)
        assertEquals(task1.hashCode(), task3.hashCode())
    }
}