package web.id.fajar.moviestar.data.factory

import web.id.fajar.moviestar.data.factory.movie.PopularMovieDataFactory
import web.id.fajar.moviestar.data.factory.movie.SearchMovieDataFactory
import web.id.fajar.moviestar.data.factory.movie.TopRatedMovieDataFactory
import web.id.fajar.moviestar.data.factory.movie.UpcomingMovieDataFactory
import web.id.fajar.moviestar.data.factory.tvshow.AiringTvShowDataFactory
import web.id.fajar.moviestar.data.factory.tvshow.PopularTvShowDataFactory
import web.id.fajar.moviestar.data.factory.tvshow.SearchTvDataFactory
import web.id.fajar.moviestar.data.factory.tvshow.TopRatedTvShowDataFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class Factory  @Inject constructor(
    val upcomingMovieDataFactory: UpcomingMovieDataFactory,
    val topRatedMovieDataFactory: TopRatedMovieDataFactory,
    val popularMovieDataFactory: PopularMovieDataFactory,
    val searchMovieDataFactory: SearchMovieDataFactory,
    val airingTvShowDataFactory: AiringTvShowDataFactory,
    val topRatedTvShowDataFactory: TopRatedTvShowDataFactory,
    val popularTvShowDataFactory: PopularTvShowDataFactory,
    val searchTvDataFactory: SearchTvDataFactory
)