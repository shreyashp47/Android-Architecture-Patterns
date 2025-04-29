package com.shreyash.mvi.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shreyash.mvi.intent.TaskIntent
import com.shreyash.mvi.state.TaskState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TaskViewModelTest {

    // Rule to make LiveData work synchronously
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Test dispatcher for coroutines
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: TaskViewModel

    @Before
    fun setUp() {
        // Set the main dispatcher for coroutines
        Dispatchers.setMain(testDispatcher)

        // Initialize the ViewModel
        viewModel = TaskViewModel()
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun testInitialState() = runTest {
        val initialState = viewModel.state.first()
        assertTrue(initialState is TaskState.Idle)
    }

    @Test
    fun testLoadTasks() = runTest {
        // Send the LoadTasks intent
        viewModel.intentChannel.send(TaskIntent.LoadTasks)

        // Get the resulting state
        val state = viewModel.state.first()

        // Verify it's a Tasks state
        assertTrue(state is TaskState.Tasks)

        // Verify it contains at least the demo task
        val tasksState = state as TaskState.Tasks
        assertTrue(tasksState.taskList.isNotEmpty())

        // Verify the demo task content
        val demoTask = tasksState.taskList.find { it.title.contains("Welcome to MVI Task App") }
        assertTrue(demoTask != null)
    }

    @Test
    fun testAddTask() = runTest {
        // First load tasks to get the initial count
        viewModel.intentChannel.send(TaskIntent.LoadTasks)
        val initialState = viewModel.state.first() as TaskState.Tasks
        val initialCount = initialState.taskList.size

        // Add a new task
        val title = "Test Task"
        val description = "Test Description"
        viewModel.intentChannel.send(TaskIntent.AddTask(title, description))

        // Get the resulting state
        val state = viewModel.state.first()

        // Verify it's a Tasks state
        assertTrue(state is TaskState.Tasks)

        // Verify the task count increased
        val tasksState = state as TaskState.Tasks
        assertEquals(initialCount + 1, tasksState.taskList.size)

        // Verify the new task is in the list
        val newTask =
            tasksState.taskList.find { it.title == title && it.description == description }
        assertTrue(newTask != null)
        assertEquals(false, newTask?.isCompleted)
    }

    @Test
    fun testAddEmptyTask() = runTest {
        // Try to add a task with empty title
        viewModel.intentChannel.send(TaskIntent.AddTask("", "Description"))

        // Get the resulting state
        val state = viewModel.state.first()

        // Verify it's an Error state
        assertTrue(state is TaskState.Error)

        // Verify the error message
        val errorState = state as TaskState.Error
        assertTrue(errorState.message.contains("empty"))
    }

    @Test
    fun testToggleTaskComplete() = runTest {
        // First load tasks
        viewModel.intentChannel.send(TaskIntent.LoadTasks)
        val initialState = viewModel.state.first() as TaskState.Tasks

        // Get the first task
        val task = initialState.taskList.first()
        val initialCompletionStatus = task.isCompleted

        // Toggle the task
        viewModel.intentChannel.send(TaskIntent.ToggleTaskComplete(task))

        // Get the resulting state
        val state = viewModel.state.first() as TaskState.Tasks

        // Find the same task in the updated list
        val updatedTask = state.taskList.find { it.id == task.id }

        // Verify the completion status was toggled
        assertTrue(updatedTask != null)
        assertEquals(!initialCompletionStatus, updatedTask?.isCompleted)
    }

    @Test
    fun testDeleteTask() = runTest {
        // First add a task to ensure we have something to delete
        val title = "Task to Delete"
        val description = "This task will be deleted"
        viewModel.intentChannel.send(TaskIntent.AddTask(title, description))

        // Get the state after adding
        val stateAfterAdd = viewModel.state.first() as TaskState.Tasks

        // Find the task we just added
        val taskToDelete = stateAfterAdd.taskList.find { it.title == title }
        assertTrue(taskToDelete != null)

        // Delete the task
        viewModel.intentChannel.send(TaskIntent.DeleteTask(taskToDelete!!))

        // Get the resulting state
        val stateAfterDelete = viewModel.state.first() as TaskState.Tasks

        // Verify the task was deleted
        val deletedTask = stateAfterDelete.taskList.find { it.id == taskToDelete.id }
        assertEquals(null, deletedTask)
    }
}