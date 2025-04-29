package com.shreyash.mvi.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.shreyash.mvi.R
import com.shreyash.mvi.model.TaskRepository
import com.shreyash.mvi.util.IdlingResourceRule
import com.shreyash.mvi.util.RecyclerViewMatchers.atPosition
import com.shreyash.mvi.util.RecyclerViewMatchers.hasItemCount
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MVIMainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MVIMainActivity::class.java)
    
    @get:Rule
    val idlingResourceRule = IdlingResourceRule()
    
    private val initialTaskCount = 1 // The repository initializes with one demo task
    
    @Before
    fun setUp() {
        // Clear any tasks from previous tests
        activityRule.scenario.onActivity { activity ->
            // We can't directly clear the repository as it's a singleton with static state
            // In a real app, you would use dependency injection to provide a test repository
        }
    }
    
    @After
    fun tearDown() {
        // Clean up after tests
    }
    
    @Test
    fun testAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assert(appContext.packageName.contains("com.shreyash.mvi"))
    }
    
    @Test
    fun testInitialUIElements() {
        // Check that all UI elements are displayed
        onView(withId(R.id.etTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.etDescription)).check(matches(isDisplayed()))
        onView(withId(R.id.btnAddTask)).check(matches(isDisplayed()))
        onView(withId(R.id.rvTasks)).check(matches(isDisplayed()))
        
        // Check that the RecyclerView has the initial demo task
        onView(withId(R.id.rvTasks)).check(matches(hasItemCount(initialTaskCount)))
        
        // Check the content of the demo task
        onView(withId(R.id.rvTasks))
            .check(matches(atPosition(0, hasDescendant(withText(containsString("Welcome to MVI Task App"))))))
    }
    
    @Test
    fun testAddTask() {
        // Define test data
        val taskTitle = "Test Task Title"
        val taskDescription = "Test Task Description"
        
        // Enter task details
        onView(withId(R.id.etTitle)).perform(clearText(), typeText(taskTitle), closeSoftKeyboard())
        onView(withId(R.id.etDescription)).perform(clearText(), typeText(taskDescription), closeSoftKeyboard())
        
        // Click add button
        onView(withId(R.id.btnAddTask)).perform(click())
        
        // Verify the task was added to the RecyclerView
        onView(withId(R.id.rvTasks)).check(matches(hasItemCount(initialTaskCount + 1)))
        
        // Verify the task content
        onView(withId(R.id.rvTasks))
            .check(matches(atPosition(initialTaskCount, allOf(
                hasDescendant(withText(taskTitle)),
                hasDescendant(withText(taskDescription))
            ))))
    }
    
    @Test
    fun testAddEmptyTask() {
        // Try to add a task with empty title and description
        onView(withId(R.id.etTitle)).perform(clearText(), closeSoftKeyboard())
        onView(withId(R.id.etDescription)).perform(clearText(), closeSoftKeyboard())
        
        // Click add button
        onView(withId(R.id.btnAddTask)).perform(click())
        
        // Verify that no new task was added (count remains the same)
        onView(withId(R.id.rvTasks)).check(matches(hasItemCount(initialTaskCount)))
    }
    
    @Test
    fun testToggleTaskComplete() {
        // First, add a new task
        val taskTitle = "Toggle Task"
        val taskDescription = "This task will be toggled"
        
        onView(withId(R.id.etTitle)).perform(clearText(), typeText(taskTitle), closeSoftKeyboard())
        onView(withId(R.id.etDescription)).perform(clearText(), typeText(taskDescription), closeSoftKeyboard())
        onView(withId(R.id.btnAddTask)).perform(click())
        
        // Verify the task was added
        onView(withId(R.id.rvTasks)).check(matches(hasItemCount(initialTaskCount + 1)))
        
        // Get the position of the newly added task
        val newTaskPosition = initialTaskCount
        
        // Check the initial state of the toggle button (should be "Mark as Done")
        onView(withId(R.id.rvTasks))
            .check(matches(atPosition(newTaskPosition, 
                hasDescendant(allOf(withId(R.id.btnToggleComplete), withText("Mark as Done")))
            )))
        
        // Click the toggle button
        onView(withId(R.id.rvTasks))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                newTaskPosition, clickChildViewWithId(R.id.btnToggleComplete)
            ))
        
        // Verify the button text changed to "Mark as Pending"
        onView(withId(R.id.rvTasks))
            .check(matches(atPosition(newTaskPosition, 
                hasDescendant(allOf(withId(R.id.btnToggleComplete), withText("Mark as Pending")))
            )))
        
        // Toggle it back
        onView(withId(R.id.rvTasks))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                newTaskPosition, clickChildViewWithId(R.id.btnToggleComplete)
            ))
        
        // Verify it changed back to "Mark as Done"
        onView(withId(R.id.rvTasks))
            .check(matches(atPosition(newTaskPosition, 
                hasDescendant(allOf(withId(R.id.btnToggleComplete), withText("Mark as Done")))
            )))
    }
    
    @Test
    fun testDeleteTask() {
        // First, add a new task
        val taskTitle = "Delete Task"
        val taskDescription = "This task will be deleted"
        
        onView(withId(R.id.etTitle)).perform(clearText(), typeText(taskTitle), closeSoftKeyboard())
        onView(withId(R.id.etDescription)).perform(clearText(), typeText(taskDescription), closeSoftKeyboard())
        onView(withId(R.id.btnAddTask)).perform(click())
        
        // Verify the task was added
        onView(withId(R.id.rvTasks)).check(matches(hasItemCount(initialTaskCount + 1)))
        
        // Get the position of the newly added task
        val newTaskPosition = initialTaskCount
        
        // Click the delete button
        onView(withId(R.id.rvTasks))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                newTaskPosition, clickChildViewWithId(R.id.btnDelete)
            ))
        
        // Verify the task was deleted (count should be back to initial)
        onView(withId(R.id.rvTasks)).check(matches(hasItemCount(initialTaskCount)))
    }
    
    @Test
    fun testAddMultipleTasks() {
        // Add first task
        onView(withId(R.id.etTitle)).perform(clearText(), typeText("Task 1"), closeSoftKeyboard())
        onView(withId(R.id.etDescription)).perform(clearText(), typeText("Description 1"), closeSoftKeyboard())
        onView(withId(R.id.btnAddTask)).perform(click())
        
        // Add second task
        onView(withId(R.id.etTitle)).perform(clearText(), typeText("Task 2"), closeSoftKeyboard())
        onView(withId(R.id.etDescription)).perform(clearText(), typeText("Description 2"), closeSoftKeyboard())
        onView(withId(R.id.btnAddTask)).perform(click())
        
        // Add third task
        onView(withId(R.id.etTitle)).perform(clearText(), typeText("Task 3"), closeSoftKeyboard())
        onView(withId(R.id.etDescription)).perform(clearText(), typeText("Description 3"), closeSoftKeyboard())
        onView(withId(R.id.btnAddTask)).perform(click())
        
        // Verify all tasks were added
        onView(withId(R.id.rvTasks)).check(matches(hasItemCount(initialTaskCount + 3)))
        
        // Verify the content of each task
        onView(withId(R.id.rvTasks))
            .check(matches(atPosition(initialTaskCount, allOf(
                hasDescendant(withText("Task 1")),
                hasDescendant(withText("Description 1"))
            ))))
        
        onView(withId(R.id.rvTasks))
            .check(matches(atPosition(initialTaskCount + 1, allOf(
                hasDescendant(withText("Task 2")),
                hasDescendant(withText("Description 2"))
            ))))
        
        onView(withId(R.id.rvTasks))
            .check(matches(atPosition(initialTaskCount + 2, allOf(
                hasDescendant(withText("Task 3")),
                hasDescendant(withText("Description 3"))
            ))))
    }
    
    /**
     * Helper method to click a child view with specified id in a RecyclerView item
     */
    private fun clickChildViewWithId(id: Int): androidx.test.espresso.ViewAction {
        return object : androidx.test.espresso.ViewAction {
            override fun getConstraints() = null
            
            override fun getDescription() = "Click on a child view with specified id."
            
            override fun perform(uiController: androidx.test.espresso.UiController, view: View) {
                val v = view.findViewById<View>(id)
                v.performClick()
            }
        }
    }
}