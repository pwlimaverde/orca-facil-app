package com.pwlimaverde.orcafacilapp.di

import android.content.Context
import androidx.room.Room
import com.pwlimaverde.orcafacilapp.data.local.AppDatabase
import com.pwlimaverde.orcafacilapp.data.local.dao.BudgetDao
import com.pwlimaverde.orcafacilapp.data.local.dao.BudgetItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "orca_facil_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBudgetDao(database: AppDatabase): BudgetDao {
        return database.budgetDao()
    }

    @Provides
    @Singleton
    fun provideBudgetItemDao(database: AppDatabase): BudgetItemDao {
        return database.budgetItemDao()
    }
}
