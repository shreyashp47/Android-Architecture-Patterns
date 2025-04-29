package com.shreyash.mvi

import com.shreyash.mvi.model.TaskRepositoryTest
import com.shreyash.mvi.view.MVIMainActivityTest
import com.shreyash.mvi.view.adapter.TaskAdapterTest
import com.shreyash.mvi.viewmodel.TaskViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Test suite that runs all tests for the MVI module
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
    MVIMainActivityTest::class,
    TaskViewModelTest::class,
    TaskRepositoryTest::class,
    TaskAdapterTest::class
)
class MVITestSuite