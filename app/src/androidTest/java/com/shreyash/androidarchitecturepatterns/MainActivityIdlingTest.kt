package com.shreyash.androidarchitecturepatterns

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shreyash.androidarchitecturepatterns.util.EspressoIdlingResource
import com.shreyash.mvc.MVCMainActivity
import com.shreyash.mvi.view.MVIMainActivity
import com.shreyash.mvp.MVPMainActivity
import com.shreyash.mvvm.view.MVVMMainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI tests for MainActivity that demonstrate the use of IdlingResources
 * for handling asynchronous operations.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityIdlingTest {

    @Before
    fun setUp() {
        // Register the idling resource
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
        
        // Initialize Intents
        Intents.init()
    }

    @After
    fun tearDown() {
        // Unregister the idling resource
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
        
        // Release Intents
        Intents.release()
    }

    /**
     * Example of how to use IdlingResource for async operations.
     * In a real scenario, you would increment() before starting an async operation
     * and decrement() when it completes.
     */
    @Test
    fun testAsyncButtonClick() {
        // Launch the activity
        ActivityScenario.launch(MainActivity::class.java)

        // Simulate an async operation starting
        EspressoIdlingResource.increment()
        
        // Perform some operation that would normally be asynchronous
        // In a real test, this would be replaced by actual async code
        Thread.sleep(500) // Simulating network delay
        
        // Simulate the async operation completing
        EspressoIdlingResource.decrement()
        
        // Now Espresso will wait until the IdlingResource is idle before continuing
        onView(withId(R.id.btn_mvc)).perform(click())
        
        // Verify that the correct activity is launched
        androidx.test.espresso.intent.Intents.intended(hasComponent(MVCMainActivity::class.java.name))
    }

    /**
     * Test all navigation buttons with idling resources
     */
    @Test
    fun testAllNavigationWithIdling() {
        // Test MVC navigation
        testNavigationWithIdling(R.id.btn_mvc, MVCMainActivity::class.java.name)
        
        // Test MVP navigation
        testNavigationWithIdling(R.id.btn_mvp, MVPMainActivity::class.java.name)
        
        // Test MVVM navigation
        testNavigationWithIdling(R.id.btn_mvvm, MVVMMainActivity::class.java.name)
        
        // Test MVI navigation
        testNavigationWithIdling(R.id.btn_mvi, MVIMainActivity::class.java.name)
    }
    
    /**
     * Helper method to test navigation with idling resources
     */
    private fun testNavigationWithIdling(buttonId: Int, targetActivityName: String) {
        // Launch the activity
        ActivityScenario.launch(MainActivity::class.java)
        
        // Simulate an async operation starting
        EspressoIdlingResource.increment()
        
        // Simulate some async operation
        Thread.sleep(200)
        
        // Simulate the async operation completing
        EspressoIdlingResource.decrement()
        
        // Click the button
        onView(withId(buttonId)).perform(click())
        
        // Verify the correct activity is launched
        androidx.test.espresso.intent.Intents.intended(hasComponent(targetActivityName))
        
        // Reset for next test
        Intents.release()
        Intents.init()
    }
}