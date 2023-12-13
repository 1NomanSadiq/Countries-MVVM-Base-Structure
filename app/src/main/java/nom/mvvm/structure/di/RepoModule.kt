package nom.mvvm.structure.di

import nom.mvvm.structure.data.database.country.CountryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nom.mvvm.structure.network.api.ApiService
import nom.mvvm.structure.network.connectivity.NetworkConnectivity
import nom.mvvm.structure.network.repository.CountriesRepo
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Singleton
    @Provides
    fun providesCountriesRepo(countryDao: CountryDao, apiService: ApiService, networkConnectivity: NetworkConnectivity) =
        CountriesRepo(countryDao, apiService, networkConnectivity)
}
