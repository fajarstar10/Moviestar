package web.id.fajar.moviestar.state

import web.id.fajar.moviestar.data.model.detailmovie.ResponseDetailMovie

sealed class DetailMovieState {
    object Loading : DetailMovieState()
    data class Result(val data : ResponseDetailMovie) : DetailMovieState()
    data class Error(val error : Throwable) : DetailMovieState()
}