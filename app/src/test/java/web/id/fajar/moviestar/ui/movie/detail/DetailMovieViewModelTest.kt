package web.id.fajar.moviestar.ui.movie.detail

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import web.id.fajar.moviestar.data.FakeRemoteRepository

class DetailMovieViewModelTest {
    lateinit var fakeRemoteRepository: FakeRemoteRepository

    @Before
    fun setUp() {
        fakeRemoteRepository = FakeRemoteRepository()
    }

    @Test
    fun testGetData() {
        val getData = fakeRemoteRepository.getDetailMovie(464052)
                .blockingGet()

        assertNotNull(getData)
        assertEquals("Wonder Woman 1984", getData.title)
    }
}