package web.id.fajar.moviestar.data.mapper

import web.id.fajar.moviestar.data.database.model.TvShowEntity
import web.id.fajar.moviestar.data.model.tvshow.DataTvShow

object TvShowMapper {
    fun mapEntityToResponse(data: TvShowEntity) =
            DataTvShow(
                    data.backdrop_path,
                    data.id, data.overview,
                    data.poster_path,
                    data.name,
                    data.first_air_date,
                    data.vote_average,
                    data.popularity
            )

    fun mapResponseToEntity(data: DataTvShow) =
            TvShowEntity(
                    data.backdrop_path,
                    data.id, data.overview,
                    data.poster_path,
                    data.name,
                    data.first_air_date,
                    data.vote_average,
                    data.popularity
            )
}
