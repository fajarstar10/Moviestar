package web.id.fajar.moviestar.ui.movie.toprated

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import web.id.fajar.moviestar.data.model.movie.DataMovie
import web.id.fajar.moviestar.data.repository.Repository
import web.id.fajar.moviestar.state.MovieState

class TopRatedMovieViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val state : MutableLiveData<MovieState> by lazy {
        MutableLiveData<MovieState>()
    }

    val data : MutableLiveData<PagedList<DataMovie>> by lazy {
        MutableLiveData<PagedList<DataMovie>>()
    }

    fun getMovie() {
        repository.getAllTopRatedMovie(state, data)
    }
}