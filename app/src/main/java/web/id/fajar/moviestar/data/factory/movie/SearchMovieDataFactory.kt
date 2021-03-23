package web.id.fajar.moviestar.data.factory.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import web.id.fajar.moviestar.data.model.movie.DataMovie
import web.id.fajar.moviestar.data.source.movie.SearchMovieDataSource
import web.id.fajar.moviestar.state.MovieState
import javax.inject.Inject

class SearchMovieDataFactory @Inject constructor(
    private val movieSearchDataSource: SearchMovieDataSource
) : DataSource.Factory<Int, DataMovie>(){

    lateinit var keyword: String
    lateinit var liveData: MutableLiveData<MovieState>

    override fun create(): DataSource<Int, DataMovie> {
        return movieSearchDataSource.also {
            it.keyword = keyword
            it.liveData = liveData
        }
    }
}