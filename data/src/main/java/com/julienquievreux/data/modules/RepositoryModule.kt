package com.julienquievreux.data.modules

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.julienquievreux.data.network.api.ContactApiService
import com.julienquievreux.data.repositories.ContactRepository
import com.julienquievreux.domain.mappers.ContactMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideContactRepository(
        apiService: ContactApiService,
        mapper: ContactMapper,
        dataStore: DataStore<Preferences>
    ): ContactRepository {
        return ContactRepository(
            apiService,
            mapper,
            dataStore
        )
    }
}