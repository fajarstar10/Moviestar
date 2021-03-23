package web.id.fajar.moviestar.data.factory.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import web.id.fajar.moviestar.data.model.movie.DataMovie
import web.id.fajar.moviestar.data.source.movie.PopularMovieDataSource
import web.id.fajar.moviestar.state.MovieState
import javax.inject.Inject

class PopularMovieDataFactory @Inject constructor(
    private val popularMovieDataSource: PopularMovieDataSource
) : DataSource.Factory<Int, DataMovie>(){

    lateinit var liveData: MutableLiveData<MovieState>

    override fun create(): DataSource<Int, DataMovie> {
        return popularMovieDataSource.also {
            it.liveData = liveData
        }
    }
}