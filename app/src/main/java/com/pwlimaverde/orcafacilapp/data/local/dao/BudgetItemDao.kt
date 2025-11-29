package com.pwlimaverde.orcafacilapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pwlimaverde.orcafacilapp.data.local.entity.BudgetItem
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: BudgetItem): Long

    @Update
    suspend fun updateItem(item: BudgetItem)

    @Delete
    suspend fun deleteItem(item: BudgetItem)

    @Query("SELECT * FROM budget_items WHERE budget_id = :budgetId")
    fun getItemsByBudget(budgetId: Long): Flow<List<BudgetItem>>
}
