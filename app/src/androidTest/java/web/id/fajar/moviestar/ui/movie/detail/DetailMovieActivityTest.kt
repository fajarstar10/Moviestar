package web.id.fajar.moviestar.ui.movie.detail

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import web.id.fajar.moviestar.R
import web.id.fajar.moviestar.data.network.ApiService
import web.id.fajar.moviestar.data.repository.Repository
import web.id.fajar.moviestar.ui.main.MainActivity
import web.id.fajar.moviestar.utils.EspressoIdlingResource
import web.id.fajar.moviestar.utils.RecyclerViewItemCountAssertion
import web.id.fajar.moviestar.utils.Utils.dateFormat
import javax.inject.Inject

@HiltAndroidTest
class DetailMovieActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Suppress("DEPRECATION")
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var endpoint: ApiService

    @Before
    fun setUp() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadDetails() {
        val getData = endpoint.getDetailMovie(602269)
            .blockingGet()

        Espresso.onView(Matchers.allOf(ViewMatchers.isDisplayed(), withId(R.id.pgLoading)))
        Espresso.onView(Matchers.allOf(ViewMatchers.isDisplayed(), withId(R.id.rvUpcoming)))
        Espresso.onView(Matchers.allOf(withId(R.id.rvUpcoming), ViewMatchers.isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
        Espresso.onView(Matchers.allOf(withId(R.id.rvUpcoming), ViewMatchers.isDisplayed()))
            .check(RecyclerViewItemCountAssertion(20))

        Espresso.onView(withId(R.id.rvUpcoming)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                ViewActions.click()
            ))

        Espresso.onView(withId(R.id.tvTitle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvTitle))
            .check(ViewAssertions.matches(withText(getData.title)))

        Espresso.onView(withId(R.id.tvDescription))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvDescription))
            .check(ViewAssertions.matches(withText(getData.overview)))

        Espresso.onView(withId(R.id.tvPopularity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvPopularity))
            .check(ViewAssertions.matches(withText(getData.popularity.toString() + ". Viewers")))

        Espresso.onView(withId(R.id.tvRating))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvRating))
            .check(ViewAssertions.matches(withText(getData.vote_average.toString())))

        Espresso.onView(withId(R.id.tvRelease))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvRelease))
            .check(
                ViewAssertions.matches(
                    withText(
                        dateFormat(
                            getData.release_date!!,
                            "yyyy-mm-dd",
                            "dd MMMM yyyy"
                        )
                    )
                )
            )
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }
}