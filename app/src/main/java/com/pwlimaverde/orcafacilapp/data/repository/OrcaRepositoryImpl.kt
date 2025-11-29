package com.pwlimaverde.orcafacilapp.data.repository

import com.pwlimaverde.orcafacilapp.data.local.dao.BudgetDao
import com.pwlimaverde.orcafacilapp.data.local.dao.BudgetItemDao
import com.pwlimaverde.orcafacilapp.data.local.entity.Budget
import com.pwlimaverde.orcafacilapp.data.local.entity.BudgetItem
import com.pwlimaverde.orcafacilapp.data.local.entity.BudgetWithItems
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrcaRepositoryImpl @Inject constructor(
    private val budgetDao: BudgetDao,
    private val budgetItemDao: BudgetItemDao
) : OrcaRepository {

    override fun getAllBudgets(): Flow<List<Budget>> {
        return budgetDao.getAllBudgets()
    }

    override fun getBudgetById(id: Long): Flow<Budget> {
        return budgetDao.getBudgetById(id)
    }

    override fun getBudgetWithItems(id: Long): Flow<BudgetWithItems> {
        return budgetDao.getBudgetWithItems(id)
    }

    override suspend fun insertBudget(budget: Budget): Long {
        return budgetDao.insertBudget(budget)
    }

    override suspend fun updateBudget(budget: Budget) {
        budgetDao.updateBudget(budget)
    }

    override suspend fun deleteBudget(budget: Budget) {
        budgetDao.deleteBudget(budget)
    }

    override fun getItemsByBudget(budgetId: Long): Flow<List<BudgetItem>> {
        return budgetItemDao.getItemsByBudget(budgetId)
    }

    override suspend fun insertItem(item: BudgetItem): Long {
        return budgetItemDao.insertItem(item)
    }

    override suspend fun updateItem(item: BudgetItem) {
        budgetItemDao.updateItem(item)
    }

    override suspend fun deleteItem(item: BudgetItem) {
        budgetItemDao.deleteItem(item)
    }
}
