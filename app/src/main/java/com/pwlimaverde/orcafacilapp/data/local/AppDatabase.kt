package com.pwlimaverde.orcafacilapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pwlimaverde.orcafacilapp.data.local.dao.BudgetDao
import com.pwlimaverde.orcafacilapp.data.local.dao.BudgetItemDao
import com.pwlimaverde.orcafacilapp.data.local.entity.Budget
import com.pwlimaverde.orcafacilapp.data.local.entity.BudgetItem

@Database(
    entities = [Budget::class, BudgetItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao
    abstract fun budgetItemDao(): BudgetItemDao
}
