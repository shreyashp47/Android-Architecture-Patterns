package com.shreyash.androidarchitecturepatterns

import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Test suite that runs all UI tests for MainActivity
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainActivityTest::class,
    MainActivityAdvancedTest::class,
    MainActivityIdlingTest::class
)
class MainActivityTestSuite