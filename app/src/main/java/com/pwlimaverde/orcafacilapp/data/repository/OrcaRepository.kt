package com.pwlimaverde.orcafacilapp.data.repository

import com.pwlimaverde.orcafacilapp.data.local.entity.Budget
import com.pwlimaverde.orcafacilapp.data.local.entity.BudgetItem
import com.pwlimaverde.orcafacilapp.data.local.entity.BudgetWithItems
import kotlinx.coroutines.flow.Flow

interface OrcaRepository {
    fun getAllBudgets(): Flow<List<Budget>>
    fun getBudgetById(id: Long): Flow<Budget>
    fun getBudgetWithItems(id: Long): Flow<BudgetWithItems>
    suspend fun insertBudget(budget: Budget): Long
    suspend fun updateBudget(budget: Budget)
    suspend fun deleteBudget(budget: Budget)

    fun getItemsByBudget(budgetId: Long): Flow<List<BudgetItem>>
    suspend fun insertItem(item: BudgetItem): Long
    suspend fun updateItem(item: BudgetItem)
    suspend fun deleteItem(item: BudgetItem)
}
