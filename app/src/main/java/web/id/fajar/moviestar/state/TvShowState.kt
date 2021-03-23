package web.id.fajar.moviestar.state

import web.id.fajar.moviestar.data.model.tvshow.ResponseTvShow

sealed class TvShowState {
    object Loading : TvShowState()
    data class Result(val data : ResponseTvShow) : TvShowState()
    data class Error(val error : Throwable) : TvShowState()
}