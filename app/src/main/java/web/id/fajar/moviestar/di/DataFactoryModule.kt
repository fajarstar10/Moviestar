package web.id.fajar.moviestar.di
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import web.id.fajar.moviestar.data.factory.Factory
import web.id.fajar.moviestar.data.factory.movie.PopularMovieDataFactory
import web.id.fajar.moviestar.data.factory.movie.SearchMovieDataFactory
import web.id.fajar.moviestar.data.factory.movie.TopRatedMovieDataFactory
import web.id.fajar.moviestar.data.factory.movie.UpcomingMovieDataFactory
import web.id.fajar.moviestar.data.factory.tvshow.AiringTvShowDataFactory
import web.id.fajar.moviestar.data.factory.tvshow.PopularTvShowDataFactory
import web.id.fajar.moviestar.data.factory.tvshow.SearchTvDataFactory
import web.id.fajar.moviestar.data.factory.tvshow.TopRatedTvShowDataFactory
import web.id.fajar.moviestar.data.source.movie.PopularMovieDataSource
import web.id.fajar.moviestar.data.source.movie.SearchMovieDataSource
import web.id.fajar.moviestar.data.source.movie.TopRatedMovieDataSource
import web.id.fajar.moviestar.data.source.movie.UpcomingMovieDataSource
import web.id.fajar.moviestar.data.source.tv.AiringTvDataSource
import web.id.fajar.moviestar.data.source.tv.PopularTvDataSource
import web.id.fajar.moviestar.data.source.tv.SearchTvDataSource
import web.id.fajar.moviestar.data.source.tv.TopRatedTvDataSource
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataFactoryModule {

    @Provides
    @Singleton
    fun provideFactory(
        upcomingMovieDataFactory: UpcomingMovieDataFactory,
        topRatedMovieDataFactory: TopRatedMovieDataFactory,
        popularMovieDataFactory: PopularMovieDataFactory,
        searchMovieDataFactory: SearchMovieDataFactory,
        airingTvShowDataFactory: AiringTvShowDataFactory,
        topRatedTvShowDataFactory: TopRatedTvShowDataFactory,
        popularTvShowDataFactory: PopularTvShowDataFactory,
        searchTvDataFactory: SearchTvDataFactory,
    ) : Factory = Factory(
        upcomingMovieDataFactory,
        topRatedMovieDataFactory,
        popularMovieDataFactory,
        searchMovieDataFactory,
        airingTvShowDataFactory,
        topRatedTvShowDataFactory,
        popularTvShowDataFactory,
        searchTvDataFactory
    )

    @Provides
    @Singleton
    fun provideUpcomingMovieFactory(
            upcomingMovieDataSource: UpcomingMovieDataSource
    ) : UpcomingMovieDataFactory = UpcomingMovieDataFactory(upcomingMovieDataSource)

    @Provides
    @Singleton
    fun provideTopRatedMovieFactory(
            topRatedMovieDataSource: TopRatedMovieDataSource
    ) : TopRatedMovieDataFactory = TopRatedMovieDataFactory(topRatedMovieDataSource)

    @Provides
    @Singleton
    fun providePopularMovieFactory(
            popularMovieDataSource: PopularMovieDataSource
    ) : PopularMovieDataFactory = PopularMovieDataFactory(popularMovieDataSource)

    @Provides
    @Singleton
    fun provideSearchMovieFactory(
            searchMovieDataSource: SearchMovieDataSource
    ) : SearchMovieDataFactory = SearchMovieDataFactory(searchMovieDataSource)

    @Provides
    @Singleton
    fun provideAiringTvDataFactory(
            airingTvDataSource: AiringTvDataSource
    ) : AiringTvShowDataFactory = AiringTvShowDataFactory(airingTvDataSource)

    @Provides
    @Singleton
    fun provideTopRatedTvDataFactory(
            topRatedTvDataSource: TopRatedTvDataSource
    ) : TopRatedTvShowDataFactory = TopRatedTvShowDataFactory(topRatedTvDataSource)

    @Provides
    @Singleton
    fun providePopularTvDataFactory(
            popularTvDataSource: PopularTvDataSource
    ) : PopularTvShowDataFactory = PopularTvShowDataFactory(popularTvDataSource)

    @Provides
    @Singleton
    fun provideSearchTvFactory(
            searchTvDataSource: SearchTvDataSource
    ) : SearchTvDataFactory = SearchTvDataFactory(searchTvDataSource)


}