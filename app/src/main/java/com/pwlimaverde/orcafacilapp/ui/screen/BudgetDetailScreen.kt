package com.pwlimaverde.orcafacilapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pwlimaverde.orcafacilapp.data.local.entity.BudgetItem
import com.pwlimaverde.orcafacilapp.ui.components.BudgetItemRow
import com.pwlimaverde.orcafacilapp.ui.viewmodel.BudgetDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetDetailScreen(
    navController: NavController,
    viewModel: BudgetDetailViewModel = hiltViewModel()
) {
    val budgetWithItems by viewModel.budgetWithItems.collectAsState()
    var showItemDialog by remember { mutableStateOf(false) }
    var itemToEdit by remember { mutableStateOf<BudgetItem?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(budgetWithItems?.budget?.nome ?: "Detalhes") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { 
                itemToEdit = null
                showItemDialog = true 
            }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Item")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            budgetWithItems?.budget?.let { budget ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Total: R$ ${String.format("%.2f", budget.valorTotal)}", style = MaterialTheme.typography.headlineSmall)
                        Text(text = budget.descricao, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            if (budgetWithItems?.items.isNullOrEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Nenhum item adicionado",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Adicione itens ao orçamento",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(budgetWithItems?.items ?: emptyList()) { item ->
                        BudgetItemRow(
                            item = item,
                            onEditClick = { 
                                itemToEdit = item
                                showItemDialog = true
                            },
                            onDeleteClick = { viewModel.deleteItem(item) }
                        )
                    }
                }
            }
        }

        if (showItemDialog) {
            AddEditItemDialog(
                item = itemToEdit,
                onDismiss = { showItemDialog = false },
                onConfirm = { nome, qtd, valor ->
                    if (itemToEdit != null) {
                        viewModel.updateItem(itemToEdit!!.copy(
                            nomeProduto = nome,
                            quantidade = qtd,
                            valorUnitario = valor
                        ))
                    } else {
                        viewModel.addItem(nome, qtd, valor)
                    }
                    showItemDialog = false
                }
            )
        }
    }
}

@Composable
fun AddEditItemDialog(
    item: BudgetItem?,
    onDismiss: () -> Unit,
    onConfirm: (String, Double, Double) -> Unit
) {
    var nome by remember { mutableStateOf(item?.nomeProduto ?: "") }
    var quantidade by remember { mutableStateOf(item?.quantidade?.toString() ?: "") }
    var valorUnitario by remember { mutableStateOf(item?.valorUnitario?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (item != null) "Editar Item" else "Novo Item") },
        text = {
            Column {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome do Produto") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = quantidade,
                    onValueChange = { quantidade = it },
                    label = { Text("Quantidade") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = valorUnitario,
                    onValueChange = { valorUnitario = it },
                    label = { Text("Valor Unitário") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val qtd = quantidade.toDoubleOrNull()
                    val valor = valorUnitario.toDoubleOrNull()
                    if (nome.isNotBlank() && qtd != null && valor != null) {
                        onConfirm(nome, qtd, valor)
                    }
                }
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
