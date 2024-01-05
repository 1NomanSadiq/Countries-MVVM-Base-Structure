package nom.mvvm.structure.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nom.mvvm.structure.utils.dispatchers.DispatchersProviders
import nom.mvvm.structure.utils.dispatchers.DispatchersProvidersImpl

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideDispatchersProvider(): DispatchersProviders {
        return DispatchersProvidersImpl
    }
}
