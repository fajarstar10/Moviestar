package web.id.fajar.moviestar.ui.movie

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import web.id.fajar.moviestar.R
import web.id.fajar.moviestar.ui.main.MainActivity
import web.id.fajar.moviestar.utils.EspressoIdlingResource
import web.id.fajar.moviestar.utils.RecyclerViewItemCountAssertion


@HiltAndroidTest
class MovieFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Suppress("DEPRECATION")
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadMovies() {
        Espresso.onView(withId(R.id.rvUpcomingMovie))
            .check(ViewAssertions.matches(isDisplayed()))

        Espresso.onView(withId(R.id.rvTopRatedMovie))
                .check(ViewAssertions.matches(isDisplayed()))

        Espresso.onView(withId(R.id.rvUpcomingMovie))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
        Espresso.onView(withId(R.id.rvUpcomingMovie))
            .check(RecyclerViewItemCountAssertion(20))

        Espresso.onView(withId(R.id.rvTopRatedMovie))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
        Espresso.onView(withId(R.id.rvTopRatedMovie))
                .check(RecyclerViewItemCountAssertion(20))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }
}