package web.id.fajar.moviestar.state

import web.id.fajar.moviestar.data.model.videos.ResponseVideo

sealed class VideoState {
    object Loading : VideoState()
    data class Result(val data : ResponseVideo) : VideoState()
    data class Error(val error : Throwable) : VideoState()
}