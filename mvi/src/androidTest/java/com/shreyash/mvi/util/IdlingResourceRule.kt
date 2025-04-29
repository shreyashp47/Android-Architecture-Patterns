package com.shreyash.mvi.util

import androidx.test.espresso.IdlingRegistry
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * JUnit rule that registers and unregisters the idling resource for tests
 */
class IdlingResourceRule : TestWatcher() {
    
    override fun starting(description: Description) {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
    }
    
    override fun finished(description: Description) {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
    }
}