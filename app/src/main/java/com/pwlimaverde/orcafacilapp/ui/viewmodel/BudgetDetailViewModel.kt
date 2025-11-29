package com.pwlimaverde.orcafacilapp.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwlimaverde.orcafacilapp.data.local.entity.Budget
import com.pwlimaverde.orcafacilapp.data.local.entity.BudgetItem
import com.pwlimaverde.orcafacilapp.data.local.entity.BudgetWithItems
import com.pwlimaverde.orcafacilapp.data.repository.OrcaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetDetailViewModel @Inject constructor(
    private val repository: OrcaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val budgetId: Long? = savedStateHandle.get<Long>("budgetId")

    @OptIn(ExperimentalCoroutinesApi::class)
    val budgetWithItems: StateFlow<BudgetWithItems?> = if (budgetId != null) {
        repository.getBudgetWithItems(budgetId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )
    } else {
        MutableStateFlow(null)
    }

    fun addItem(nomeProduto: String, quantidade: Double, valorUnitario: Double) {
        val currentBudget = budgetWithItems.value?.budget ?: return
        
        viewModelScope.launch {
            val newItem = BudgetItem(
                budgetId = currentBudget.id,
                nomeProduto = nomeProduto,
                quantidade = quantidade,
                valorUnitario = valorUnitario
            )
            repository.insertItem(newItem)
            updateBudgetTotal(currentBudget)
        }
    }

    fun updateItem(item: BudgetItem) {
        val currentBudget = budgetWithItems.value?.budget ?: return
        viewModelScope.launch {
            repository.updateItem(item)
            updateBudgetTotal(currentBudget)
        }
    }

    fun deleteItem(item: BudgetItem) {
        val currentBudget = budgetWithItems.value?.budget ?: return
        viewModelScope.launch {
            repository.deleteItem(item)
            updateBudgetTotal(currentBudget)
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
    
    // Método para atualizar o status ou outros dados do orçamento
    fun updateBudget(budget: Budget) {
        viewModelScope.launch {
            repository.updateBudget(budget)
        }
    }
}
