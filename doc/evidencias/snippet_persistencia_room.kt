// Trecho extraído de: data/local/dao/BudgetDao.kt
// Interface DAO com anotações do Room para persistência de dados

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
