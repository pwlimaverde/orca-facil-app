// Trecho extraído de: ui/viewmodel/BudgetDetailViewModel.kt
// Lógica de adição de item e atualização do valor total do orçamento

fun addItem(nomeProduto: String, quantidade: Double, valorUnitario: Double) {
    val currentBudget = budgetWithItems.value?.budget ?: return
    
    viewModelScope.launch {
        try {
            val newItem = BudgetItem(
                budgetId = currentBudget.id,
                nomeProduto = nomeProduto,
                quantidade = quantidade,
                valorUnitario = valorUnitario
            )
            repository.insertItem(newItem)
            updateBudgetTotal(currentBudget)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

private suspend fun updateBudgetTotal(budget: Budget) {
    // Coleta única dos itens para calcular o novo total
    try {
        val items = repository.getItemsByBudget(budget.id).first()
        val total = items.sumOf { it.quantidade * it.valorUnitario }
        val updatedBudget = budget.copy(valorTotal = total)
        repository.updateBudget(updatedBudget)
    } catch (e: Exception) {
        // Log error
        e.printStackTrace()
    }
}
