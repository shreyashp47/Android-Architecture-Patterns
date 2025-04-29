package com.shreyash.androidarchitecturepatterns

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.shreyash.mvc.MVCMainActivity
import com.shreyash.mvi.view.MVIMainActivity
import com.shreyash.mvp.MVPMainActivity
import com.shreyash.mvvm.view.MVVMMainActivity
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityAdvancedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testAppContext() {
        // Test that the context of the app is correct
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assert(appContext.packageName == "com.shreyash.androidarchitecturepatterns")
    }

    @Test
    fun testMainActivityLayout() {
        // Check that the main layout is displayed
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    fun testAllButtonsEnabled() {
        // Check that all buttons are enabled
        onView(withId(R.id.btn_mvc)).check(matches(isEnabled()))
        onView(withId(R.id.btn_mvp)).check(matches(isEnabled()))
        onView(withId(R.id.btn_mvvm)).check(matches(isEnabled()))
        onView(withId(R.id.btn_mvi)).check(matches(isEnabled()))
    }

    @Test
    fun testButtonsVisibility() {
        // Check that all buttons are visible
        onView(withId(R.id.btn_mvc)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.btn_mvp)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.btn_mvvm)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.btn_mvi)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun testSequentialButtonClicks() {
        // Test clicking each button in sequence
        
        // Click MVC button and verify intent
        onView(withId(R.id.btn_mvc)).perform(click())
        androidx.test.espresso.intent.Intents.intended(hasComponent(MVCMainActivity::class.java.name))
        
        // Go back to MainActivity
        Intents.release()
        Intents.init()
        
        // Launch activity again
        ActivityScenario.launch(MainActivity::class.java)
        
        // Click MVP button and verify intent
        onView(withId(R.id.btn_mvp)).perform(click())
        androidx.test.espresso.intent.Intents.intended(hasComponent(MVPMainActivity::class.java.name))
        
        // Go back to MainActivity
        Intents.release()
        Intents.init()
        
        // Launch activity again
        ActivityScenario.launch(MainActivity::class.java)
        
        // Click MVVM button and verify intent
        onView(withId(R.id.btn_mvvm)).perform(click())
        androidx.test.espresso.intent.Intents.intended(hasComponent(MVVMMainActivity::class.java.name))
        
        // Go back to MainActivity
        Intents.release()
        Intents.init()
        
        // Launch activity again
        ActivityScenario.launch(MainActivity::class.java)
        
        // Click MVI button and verify intent
        onView(withId(R.id.btn_mvi)).perform(click())
        androidx.test.espresso.intent.Intents.intended(hasComponent(MVIMainActivity::class.java.name))
    }

    @Test
    fun testButtonClickPerformance() {
        // Test the performance of button clicks using a custom ViewAction
        onView(withId(R.id.btn_mvc)).perform(clickWithTimeMeasurement())
        onView(withId(R.id.btn_mvp)).perform(clickWithTimeMeasurement())
        onView(withId(R.id.btn_mvvm)).perform(clickWithTimeMeasurement())
        onView(withId(R.id.btn_mvi)).perform(clickWithTimeMeasurement())
    }

    /**
     * Custom ViewAction to measure the time it takes to perform a click
     */
    private fun clickWithTimeMeasurement(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isEnabled()
            }

            override fun getDescription(): String {
                return "Click with time measurement"
            }

            override fun perform(uiController: UiController, view: View) {
                val startTime = System.currentTimeMillis()
                click().perform(uiController, view)
                val endTime = System.currentTimeMillis()
                val elapsedTime = endTime - startTime
                println("Button click took $elapsedTime ms")
            }
        }
    }
}