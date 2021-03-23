package web.id.fajar.moviestar.state

import web.id.fajar.moviestar.data.model.movie.ResponseMovie

sealed class MovieState {
    object Loading : MovieState()
    data class Result(val data : ResponseMovie) : MovieState()
    data class Error(val error : Throwable) : MovieState()
}