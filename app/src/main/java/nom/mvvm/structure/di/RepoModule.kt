package nom.mvvm.structure.di

import com.truebilling.truechargecapture.drs.data.database.providerfacility.CountryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nom.mvvm.structure.network.api.ApiService
import nom.mvvm.structure.network.repository.CountriesRepo
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Singleton
    @Provides
    fun providesCountriesRepo(countryDao: CountryDao, apiService: ApiService) =
        CountriesRepo(countryDao, apiService)
}
