package com.pwlimaverde.orcafacilapp.di

import com.pwlimaverde.orcafacilapp.data.repository.OrcaRepository
import com.pwlimaverde.orcafacilapp.data.repository.OrcaRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindOrcaRepository(
        orcaRepositoryImpl: OrcaRepositoryImpl
    ): OrcaRepository
}
