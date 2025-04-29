package com.shreyash.androidarchitecturepatterns

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shreyash.mvc.MVCMainActivity
import com.shreyash.mvi.view.MVIMainActivity
import com.shreyash.mvp.MVPMainActivity
import com.shreyash.mvvm.view.MVVMMainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

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
    fun testUIElementsDisplayed() {
        // Launch the activity
        ActivityScenario.launch(MainActivity::class.java)

        // Check if all buttons are displayed
        onView(withId(R.id.btn_mvc)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_mvp)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_mvvm)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_mvi)).check(matches(isDisplayed()))

        // Check if buttons have correct text
        onView(withId(R.id.btn_mvc)).check(matches(withText("Open MVC")))
        onView(withId(R.id.btn_mvp)).check(matches(withText("Open MVP")))
        onView(withId(R.id.btn_mvvm)).check(matches(withText("Open MVVM")))
        onView(withId(R.id.btn_mvi)).check(matches(withText("Open MVI")))
    }

    @Test
    fun testMVCButtonNavigation() {
        // Launch the activity
        ActivityScenario.launch(MainActivity::class.java)

        // Click on MVC button
        onView(withId(R.id.btn_mvc)).perform(click())

        // Verify that the correct activity is launched
        intended(hasComponent(MVCMainActivity::class.java.name))
    }

    @Test
    fun testMVPButtonNavigation() {
        // Launch the activity
        ActivityScenario.launch(MainActivity::class.java)

        // Click on MVP button
        onView(withId(R.id.btn_mvp)).perform(click())

        // Verify that the correct activity is launched
        intended(hasComponent(MVPMainActivity::class.java.name))
    }

    @Test
    fun testMVVMButtonNavigation() {
        // Launch the activity
        ActivityScenario.launch(MainActivity::class.java)

        // Click on MVVM button
        onView(withId(R.id.btn_mvvm)).perform(click())

        // Verify that the correct activity is launched
        intended(hasComponent(MVVMMainActivity::class.java.name))
    }

    @Test
    fun testMVIButtonNavigation() {
        // Launch the activity
        ActivityScenario.launch(MainActivity::class.java)

        // Click on MVI button
        onView(withId(R.id.btn_mvi)).perform(click())

        // Verify that the correct activity is launched
        intended(hasComponent(MVIMainActivity::class.java.name))
    }
}