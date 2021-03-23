package web.id.fajar.moviestar.ui.tvshow.detail

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import web.id.fajar.moviestar.data.FakeRemoteRepository

class DetailTvShowViewModelTest {
    lateinit var fakeRemoteRepository: FakeRemoteRepository

    @Before
    fun setUp() {
        fakeRemoteRepository = FakeRemoteRepository()
    }

    @Test
    fun testGetData() {
        val getData = fakeRemoteRepository.getDetailTv(85271)
                .blockingGet()

        assertNotNull(getData)
        assertEquals("WandaVision", getData.name)
    }
}