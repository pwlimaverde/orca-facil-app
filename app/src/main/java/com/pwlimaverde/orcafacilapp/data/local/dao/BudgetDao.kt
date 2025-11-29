package com.pwlimaverde.orcafacilapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.pwlimaverde.orcafacilapp.data.local.entity.Budget
import com.pwlimaverde.orcafacilapp.data.local.entity.BudgetWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: Budget): Long

    @Update
    suspend fun updateBudget(budget: Budget)

    @Delete
    suspend fun deleteBudget(budget: Budget)

    @Query("SELECT * FROM budgets ORDER BY data_criacao DESC")
    fun getAllBudgets(): Flow<List<Budget>>

    @Query("SELECT * FROM budgets WHERE id = :id")
    fun getBudgetById(id: Long): Flow<Budget>

    @Transaction
    @Query("SELECT * FROM budgets WHERE id = :id")
    fun getBudgetWithItems(id: Long): Flow<BudgetWithItems>
}
