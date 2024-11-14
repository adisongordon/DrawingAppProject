package com.example.drawingapp

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.graphics.Bitmap
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
/*
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    class TestObserver<T> : Observer<T> {
        private var observedValue: T? = null
        override fun onChanged(value: T) {
            observedValue = value
        }
    }

    private lateinit var viewModel: DrawingViewModel
    private lateinit var bitmapOb: TestObserver<Bitmap>

    // Sets up the test environment before each test
    @Before
    fun setup() {
        bitmapOb = TestObserver()
        viewModel = DrawingViewModel()
        viewModel.bitmap.observeForever(bitmapOb)
    }

    // Tests for the drawing screen coming up
    @Test
    fun checkDrawingScreen() {
        Thread.sleep(3000)
        onView(withId(R.id.canvas_view)).perform(click()).check(matches(isDisplayed()))
    }

    // Tests for the main screen coming up
    @Test
    fun checkMainActivityVisibility() {
        Thread.sleep(3000) // Waits for the splash screen to load
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @RunWith(AndroidJUnit4::class)
    class DatabaseDrawingTest {
        private lateinit var database: DrawingDatabase
        private lateinit var dao: DrawingDAO

        // Sets up the test environment before each test for database
        @Before
        fun createDb() {
            val context = ApplicationProvider.getApplicationContext<Context>()
            database = Room.inMemoryDatabaseBuilder(context, DrawingDatabase::class.java)
                .allowMainThreadQueries()
                .build()
            dao = database.drawingDAO()
        }
        //Closes database after the tests are done
        @After
        fun closeDb() {
            database.close()
        }
        // Tests for inserting drawings and getting them back
        @Test
        fun insertAndRetrieve() = runBlocking {
            val userDrawing = DrawingEntity(name = "Test Drawing", filePath = "/path/to/drawing")
            dao.insert(userDrawing)

            val byName = dao.getDrawingByName("Test Drawing")
            assertEquals(byName?.name, userDrawing.name)
            assertEquals(byName?.filePath, userDrawing.filePath)
        }
        // Tests for getting multiple drawings
        @Test
        fun getDrawings() = runBlocking {
            val firstDrawing = DrawingEntity(name = "First Drawing", filePath = "/path/to/firstDrawing")
            val secondDrawing = DrawingEntity(name = "Second Drawing", filePath = "/path/to/secondDrawing")
            dao.insert(firstDrawing)
            dao.insert(secondDrawing)

            val drawingCollection = dao.getAllDrawings()
            assertEquals(drawingCollection.size, 2)
        }
    }
}
*/