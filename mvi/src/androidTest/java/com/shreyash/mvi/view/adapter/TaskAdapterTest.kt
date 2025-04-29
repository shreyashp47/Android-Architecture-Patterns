package com.shreyash.mvi.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shreyash.mvi.databinding.ItemTaskBinding
import com.shreyash.mvi.model.Task
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class TaskAdapterTest {

    private lateinit var context: Context
    private lateinit var tasks: MutableList<Task>
    private lateinit var adapter: TaskAdapter
    private var toggleTaskCalled = false
    private var deleteTaskCalled = false
    private var toggledTask: Task? = null
    private var deletedTask: Task? = null
    
    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        
        // Create test tasks
        tasks = mutableListOf(
            Task(1, "Task 1", "Description 1", false),
            Task(2, "Task 2", "Description 2", true),
            Task(3, "Task 3", "Description 3", false)
        )
        
        // Create adapter with callback tracking
        adapter = TaskAdapter(
            tasks,
            onToggleComplete = { task ->
                toggleTaskCalled = true
                toggledTask = task
            },
            onDelete = { task ->
                deleteTaskCalled = true
                deletedTask = task
            }
        )
        
        // Reset tracking variables
        toggleTaskCalled = false
        deleteTaskCalled = false
        toggledTask = null
        deletedTask = null
    }
    
    @Test
    fun testGetItemCount() {
        // Verify the item count matches the task list size
        assertEquals(tasks.size, adapter.itemCount)
        
        // Add a task and verify the count updates
        tasks.add(Task(4, "Task 4", "Description 4"))
        assertEquals(tasks.size, adapter.itemCount)
    }
    
    @Test
    fun testOnBindViewHolder() {
        // Create a mock ViewHolder
        val inflater = LayoutInflater.from(context)
        val binding = ItemTaskBinding.inflate(inflater)
        val holder = adapter.TaskViewHolder(binding)
        
        // Bind the first task
        adapter.onBindViewHolder(holder, 0)
        
        // Verify the views were updated correctly
        assertEquals("Task 1", holder.binding.tvTitle.text.toString())
        assertEquals("Description 1", holder.binding.tvDescription.text.toString())
        assertEquals("Mark as Done", holder.binding.btnToggleComplete.text.toString())
        
        // Bind the second task (which is completed)
        adapter.onBindViewHolder(holder, 1)
        
        // Verify the views were updated correctly
        assertEquals("Task 2", holder.binding.tvTitle.text.toString())
        assertEquals("Description 2", holder.binding.tvDescription.text.toString())
        assertEquals("Mark as Pending", holder.binding.btnToggleComplete.text.toString())
    }
    
    @Test
    fun testToggleButtonClick() {
        // Create a ViewHolder
        val inflater = LayoutInflater.from(context)
        val binding = ItemTaskBinding.inflate(inflater)
        val holder = adapter.TaskViewHolder(binding)
        
        // Bind the first task
        adapter.onBindViewHolder(holder, 0)
        
        // Click the toggle button
        holder.binding.btnToggleComplete.performClick()
        
        // Verify the callback was called with the correct task
        assertTrue(toggleTaskCalled)
        assertEquals(tasks[0], toggledTask)
        assertFalse(deleteTaskCalled)
    }
    
    @Test
    fun testDeleteButtonClick() {
        // Create a ViewHolder
        val inflater = LayoutInflater.from(context)
        val binding = ItemTaskBinding.inflate(inflater)
        val holder = adapter.TaskViewHolder(binding)
        
        // Bind the first task
        adapter.onBindViewHolder(holder, 0)
        
        // Click the delete button
        holder.binding.btnDelete.performClick()
        
        // Verify the callback was called with the correct task
        assertTrue(deleteTaskCalled)
        assertEquals(tasks[0], deletedTask)
        assertFalse(toggleTaskCalled)
    }
}