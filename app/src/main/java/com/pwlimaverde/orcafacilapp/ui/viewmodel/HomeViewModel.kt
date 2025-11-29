package com.pwlimaverde.orcafacilapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwlimaverde.orcafacilapp.data.local.entity.Budget
import com.pwlimaverde.orcafacilapp.data.repository.OrcaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: OrcaRepository
) : ViewModel() {

    val budgets: StateFlow<List<Budget>> = repository.getAllBudgets()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addBudget(nome: String, descricao: String) {
        viewModelScope.launch {
            val newBudget = Budget(
                nome = nome,
                descricao = descricao,
                dataCriacao = System.currentTimeMillis(),
                valorTotal = 0.0,
                status = "Em Aberto"
            )
            repository.insertBudget(newBudget)
        }
    }

    fun deleteBudget(budget: Budget) {
        viewModelScope.launch {
            repository.deleteBudget(budget)
        }
    }
}
