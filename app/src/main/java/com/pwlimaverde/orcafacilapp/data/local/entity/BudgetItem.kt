package com.pwlimaverde.orcafacilapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "budget_items",
    foreignKeys = [
        ForeignKey(
            entity = Budget::class,
            parentColumns = ["id"],
            childColumns = ["budget_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["budget_id"])]
)
data class BudgetItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "budget_id")
    val budgetId: Long,

    @ColumnInfo(name = "nome_produto")
    val nomeProduto: String,

    @ColumnInfo(name = "valor_unitario")
    val valorUnitario: Double,

    @ColumnInfo(name = "quantidade")
    val quantidade: Double
)
