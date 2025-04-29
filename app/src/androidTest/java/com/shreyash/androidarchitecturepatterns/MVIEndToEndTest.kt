package com.shreyash.androidarchitecturepatterns

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shreyash.mvi.view.MVIMainActivity
import kotlinx.coroutines.delay
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MVIEndToEndTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        // Initialize Intents before each test
        Intents.init()
    }

    @After
    fun tearDown() {
        // Release Intents after each test
        Intents.release()
    }

    @Test
    fun testMVITaskManagement() {
        // 1. Start from MainActivity and click on MVI button
        onView(withId(R.id.btn_mvi)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_mvi)).perform(click())

        // 2. Verify navigation to MVIMainActivity
        Intents.intended(hasComponent(MVIMainActivity::class.java.name))

        // 3. Verify MVI UI elements are displayed
        onView(withId(com.shreyash.mvi.R.id.etTitle)).check(matches(isDisplayed()))
        onView(withId(com.shreyash.mvi.R.id.etDescription)).check(matches(isDisplayed()))
        onView(withId(com.shreyash.mvi.R.id.btnAddTask)).check(matches(isDisplayed()))
        onView(withId(com.shreyash.mvi.R.id.rvTasks)).check(matches(isDisplayed()))

        // 4. Enter task details
        val taskTitle = "E2E Test Task"
        val taskDescription = "This is an end-to-end test task"

        Thread.sleep(500)
        
        onView(withId(com.shreyash.mvi.R.id.etTitle))
            .perform(clearText(), typeText(taskTitle), closeSoftKeyboard())
        
        onView(withId(com.shreyash.mvi.R.id.etDescription))
            .perform(clearText(), typeText(taskDescription), closeSoftKeyboard())

        // 5. Add the task
        onView(withId(com.shreyash.mvi.R.id.btnAddTask)).perform(click())

        // 6. Verify the task was added to the RecyclerView
        // First, find the position of our newly added task
        // Note: The repository initializes with one demo task, so our task should be at position 1
        val taskPosition = 1 // Position of our newly added task (0-based index)
        Thread.sleep(500)
        // Verify the task content in the RecyclerView
        onView(withId(com.shreyash.mvi.R.id.rvTasks))
            .check(matches(atPosition(taskPosition, allOf(
                hasDescendant(withText(taskTitle)),
                hasDescendant(withText(taskDescription)),
                hasDescendant(allOf(withId(com.shreyash.mvi.R.id.btnToggleComplete), withText("Mark as Done"))),
                hasDescendant(withId(com.shreyash.mvi.R.id.btnDelete))
            ))))
        Thread.sleep(500)
        // 7. Test the Toggle Complete button
        onView(withId(com.shreyash.mvi.R.id.rvTasks))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                taskPosition, clickChildViewWithId(com.shreyash.mvi.R.id.btnToggleComplete)
            ))
        Thread.sleep(500)
        // 8. Verify the button text changed to "Mark as Pending"
        onView(withId(com.shreyash.mvi.R.id.rvTasks))
            .check(matches(atPosition(taskPosition, 
                hasDescendant(allOf(withId(com.shreyash.mvi.R.id.btnToggleComplete), withText("Mark as Pending")))
            )))
        Thread.sleep(500)
        // 9. Toggle it back
        onView(withId(com.shreyash.mvi.R.id.rvTasks))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                taskPosition, clickChildViewWithId(com.shreyash.mvi.R.id.btnToggleComplete)
            ))
        Thread.sleep(500)
        // 10. Verify it changed back to "Mark as Done"
        onView(withId(com.shreyash.mvi.R.id.rvTasks))
            .check(matches(atPosition(taskPosition, 
                hasDescendant(allOf(withId(com.shreyash.mvi.R.id.btnToggleComplete), withText("Mark as Done")))
            )))
        Thread.sleep(500)
        // 11. Test the Delete button
        onView(withId(com.shreyash.mvi.R.id.rvTasks))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                taskPosition, clickChildViewWithId(com.shreyash.mvi.R.id.btnDelete)
            ))
        Thread.sleep(500)
        // 12. Verify the task was deleted (it should no longer be in the list)
        // We can verify this by checking that the RecyclerView no longer has an item with our task title
        onView(withId(com.shreyash.mvi.R.id.rvTasks))
            .check(doesNotHaveItemWithText(taskTitle))
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

    /**
     * Custom matcher for RecyclerView items at a specific position
     */
    private fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
        return object : org.hamcrest.TypeSafeMatcher<View>() {
            override fun describeTo(description: org.hamcrest.Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                val recyclerView = view as RecyclerView
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                    ?: return false // has no item at this position
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

    /**
     * Custom ViewAssertion to check that a RecyclerView does not have an item with specific text
     */
    private fun doesNotHaveItemWithText(text: String): androidx.test.espresso.ViewAssertion {
        return androidx.test.espresso.ViewAssertion { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter!!
            
            var foundItem = false
            for (i in 0 until adapter.itemCount) {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(i)
                if (viewHolder != null) {
                    val titleTextView = viewHolder.itemView.findViewById<android.widget.TextView>(com.shreyash.mvi.R.id.tvTitle)
                    if (titleTextView.text.toString() == text) {
                        foundItem = true
                        break
                    }
                }
            }
            
            if (foundItem) {
                throw AssertionError("RecyclerView contains an item with text: $text")
            }
        }
    }
}