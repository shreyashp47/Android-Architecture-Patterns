package com.shreyash.androidarchitecturepatterns.util

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Utility class for handling asynchronous operations in UI tests
 */
object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"
    
    private val countingIdlingResource = CountingIdlingResource(RESOURCE)
    
    fun increment() {
        countingIdlingResource.increment()
    }
    
    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
    
    fun getIdlingResource(): IdlingResource {
        return countingIdlingResource
    }
}