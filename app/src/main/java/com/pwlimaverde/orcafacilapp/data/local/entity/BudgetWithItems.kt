package com.pwlimaverde.orcafacilapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class BudgetWithItems(
    @Embedded val budget: Budget,
    @Relation(
        parentColumn = "id",
        entityColumn = "budget_id"
    )
    val items: List<BudgetItem>
)
