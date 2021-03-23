package web.id.fajar.moviestar.state

import web.id.fajar.moviestar.data.model.detailtv.ResponseDetailTv

sealed class DetailTvShowState {
    object Loading : DetailTvShowState()
    data class Result(val data : ResponseDetailTv) : DetailTvShowState()
    data class Error(val error : Throwable) : DetailTvShowState()
}