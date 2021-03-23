package web.id.fajar.moviestar.di

import androidx.paging.PagedList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import web.id.fajar.moviestar.data.database.RoomDb
import web.id.fajar.moviestar.data.factory.Factory
import web.id.fajar.moviestar.data.network.ApiService
import web.id.fajar.moviestar.data.repository.DataRepository
import web.id.fajar.moviestar.data.repository.local.LocalRepository
import web.id.fajar.moviestar.data.repository.remote.RemoteRepository
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRemoteRepository(
        apiService: ApiService,
        config : PagedList.Config,
        factory : Factory
    ) : RemoteRepository = RemoteRepository(
            apiService,
            config,
            factory
        )

    @Singleton
    @Provides
    fun provideLocalRepository(
        database : RoomDb,
        config : PagedList.Config
    ) : LocalRepository = LocalRepository(database, config)


    @Singleton
    @Provides
    fun provideDataRepository(
        remoteRepository: RemoteRepository,
        localRepository: LocalRepository
    ) : DataRepository = DataRepository(
        remoteRepository,
        localRepository
    )
}