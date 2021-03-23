package web.id.fajar.moviestar.data.mapper

import web.id.fajar.moviestar.data.database.model.MovieEntity
import web.id.fajar.moviestar.data.model.movie.DataMovie

object MovieMapper {
    fun mapEntityToResponse(data: MovieEntity) =
            DataMovie(
                    data.backdrop_path,
                    data.id, data.overview,
                    data.poster_path,
                    data.title,
                    data.release_date,
                    data.vote_average,
                    data.popularity,
                    genreIds = null,
                    movieType = ""
            )

    fun mapResponseToEntity(data: DataMovie) =
            MovieEntity(
                    data.backdrop_path,
                    data.id, data.overview,
                    data.poster_path,
                    data.title,
                    data.release_date,
                    data.vote_average,
                    data.popularity,
                    data.genreIds?.get(0).toString()
            )
}
