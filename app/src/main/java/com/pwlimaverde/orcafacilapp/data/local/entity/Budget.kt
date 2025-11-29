package com.pwlimaverde.orcafacilapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class Budget(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "nome")
    val nome: String, // Cliente ou Título

    @ColumnInfo(name = "descricao")
    val descricao: String,

    @ColumnInfo(name = "valor_total")
    val valorTotal: Double = 0.0,

    @ColumnInfo(name = "status")
    val status: String = "Em Aberto",

    @ColumnInfo(name = "data_criacao")
    val dataCriacao: Long = System.currentTimeMillis()
)
