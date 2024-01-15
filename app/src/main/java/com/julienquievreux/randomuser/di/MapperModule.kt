package com.julienquievreux.randomuser.di

import com.julienquievreux.domain.mappers.ContactMapper
import com.julienquievreux.service.implementations.ContactMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MappersModule {
    @Provides
    fun provideContactMapper(): ContactMapper {
        return ContactMapperImpl()
    }
}