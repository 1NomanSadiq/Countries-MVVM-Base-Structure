package nom.mvvm.structure.di

import nom.mvvm.structure.network.api.ApiService
import nom.mvvm.structure.network.repository.CountriesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Singleton
    @Provides
    fun providesCountriesRepo(apiService: ApiService) =
        CountriesRepo(apiService)
}
