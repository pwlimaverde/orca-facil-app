# Registro Técnico - Camada de Dados DAO (Task 4)

Este documento registra a implementação da camada de acesso a dados (DAO) e configuração do banco de dados Room, detalhando as decisões para a seção de **Implementação** do Paper.

## 1. Data Access Objects (DAOs)

Os DAOs foram implementados como interfaces, utilizando as anotações do Room para mapear métodos Kotlin para comandos SQL. Adotou-se uma abordagem assíncrona utilizando **Kotlin Coroutines** (`suspend functions`) para operações de escrita e **Kotlin Flow** para operações de leitura reativa.

### BudgetDao
Responsável pelas operações na tabela `budgets`.
*   `insertBudget(budget: Budget): Long`: Insere um orçamento e retorna seu ID gerado. Usa `OnConflictStrategy.REPLACE`.
*   `getAllBudgets(): Flow<List<Budget>>`: Retorna um fluxo contínuo de todos os orçamentos, ordenados por data de criação (mais recentes primeiro).
*   `getBudgetWithItems(id: Long): Flow<BudgetWithItems>`: Utiliza a anotação `@Transaction` para garantir a atomicidade ao buscar o orçamento e seus itens relacionados em uma única operação lógica.

### BudgetItemDao
Responsável pelas operações na tabela `budget_items`.
*   `insertItem(item: BudgetItem): Long`: Insere um item.
*   `getItemsByBudget(budgetId: Long): Flow<List<BudgetItem>>`: Busca itens de um orçamento específico.

## 2. Configuração do Banco de Dados (AppDatabase)

A classe abstrata `AppDatabase` herda de `RoomDatabase` e serve como ponto central de acesso.
*   **Entidades:** `Budget`, `BudgetItem`.
*   **Versão:** 1.
*   **Export Schema:** Desabilitado (`false`) para esta fase inicial, simplificando o build.

## 3. Injeção de Dependência (Hilt)

Foi criado o módulo `DatabaseModule` (`@InstallIn(SingletonComponent::class)`) para prover as instâncias do banco e dos DAOs para toda a aplicação.

```kotlin
@Provides
@Singleton
fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "orca_facil_db"
    ).build()
}
```

*   **Padrão Singleton:** Garante que apenas uma conexão com o banco de dados seja aberta durante o ciclo de vida do app, otimizando recursos.

## 4. Validação Técnica
O projeto foi compilado com sucesso, confirmando a correta geração de código pelo KSP (Kotlin Symbol Processing) para as implementações dos DAOs e do Database.
