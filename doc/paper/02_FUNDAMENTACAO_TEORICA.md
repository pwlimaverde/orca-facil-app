# 2. Fundamentação Teórica

O desenvolvimento de aplicações móveis modernas exige ferramentas que proporcionem eficiência, segurança e manutenibilidade. Neste contexto, a linguagem **Kotlin** consolidou-se como a escolha preferencial para o ecossistema Android, recomendada oficialmente pela Google. Kotlin é uma linguagem estaticamente tipada que roda na Máquina Virtual Java (JVM), projetada para ser totalmente interoperável com Java, porém corrigindo muitas de suas limitações históricas, como a verbosidade excessiva e a propensão a erros de referência nula (*NullPointerException*).

Um dos pilares da modernização no desenvolvimento Android é o **Android Jetpack**, um conjunto de bibliotecas e ferramentas que ajudam os desenvolvedores a seguir as melhores práticas, reduzir o código repetitivo e escrever código que funciona consistentemente em diferentes versões do Android. Dentro do Jetpack, destaca-se o **Jetpack Compose**, o kit de ferramentas moderno para construção de interfaces nativas. Diferente do sistema de Views tradicional baseado em XML, o Compose utiliza uma abordagem **declarativa**, onde a interface do usuário é descrita em código Kotlin e atualizada automaticamente em resposta a mudanças de estado.

A persistência de dados local é fundamental para garantir que o aplicativo funcione offline e mantenha os dados do usuário seguros. Para isso, utilizamos a biblioteca **Room**, que faz parte do Android Jetpack. O Room fornece uma camada de abstração sobre o SQLite, permitindo um acesso mais robusto ao banco de dados enquanto aproveita todo o poder da linguagem SQL. Ele simplifica consideravelmente o código necessário para configurar, configurar e interagir com o banco de dados local, utilizando anotações para definir entidades e objetos de acesso a dados (DAOs).

No projeto **OrcaFacilApp**, a arquitetura adotada segue o padrão recomendado pela Google, separando claramente as responsabilidades entre Interface de Usuário (UI), ViewModel e Camada de Dados (Repository e Data Source). Essa separação é crucial para a testabilidade e escalabilidade do aplicativo. A camada de dados utiliza o Room para gerenciar as operações de banco de dados de forma assíncrona, garantindo que a interface do usuário permaneça responsiva.

Abaixo, apresentamos um exemplo prático da implementação da camada de persistência no projeto, demonstrando como o Room utiliza interfaces e anotações para definir as operações de banco de dados. Este código foi extraído diretamente do arquivo `BudgetDao.kt` do projeto:

```kotlin
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
```

Como observado no código acima, as anotações `@Insert`, `@Update`, `@Delete` e `@Query` eliminam a necessidade de escrever código repetitivo de manipulação de cursores e transações SQL manualmente. O uso de `Flow` e funções `suspend` integra-se perfeitamente com as **Kotlin Coroutines**, permitindo que as operações de banco de dados sejam executadas fora da thread principal, prevenindo travamentos na interface.

Outro aspecto vital é a gestão de estado e a lógica de negócios, que residem nos **ViewModels**. O ViewModel é responsável por preparar e gerenciar os dados para a UI, além de lidar com a comunicação com o Repositório. No OrcaFacilApp, utilizamos `StateFlow` para expor o estado da UI de forma reativa. Quando os dados no banco de dados mudam, o Room emite novos valores através do Flow, que são processados pelo ViewModel e consumidos pela interface em Compose.

A seguir, um trecho do `BudgetDetailViewModel.kt` ilustra como a lógica de cálculo e atualização é encapsulada. Observe como o ViewModel interage com o repositório e utiliza corrotinas para realizar operações de forma segura:

```kotlin
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
```

Este trecho demonstra a aplicação prática de regras de negócio dentro do ViewModel: ao adicionar um item, o sistema não apenas insere o registro, mas também recalcula automaticamente o valor total do orçamento, garantindo a consistência dos dados apresentados ao usuário. A utilização do `viewModelScope` assegura que essas operações respeitem o ciclo de vida da ViewModel, sendo canceladas automaticamente se o usuário sair da tela, evitando vazamentos de memória.

Por fim, a integração entre essas tecnologias – Kotlin, Room, Coroutines e Jetpack Compose – resulta em um código conciso, legível e seguro. A tipagem estática do Kotlin e as verificações em tempo de compilação do Room (que valida as queries SQL durante o build) reduzem drasticamente a ocorrência de erros em tempo de execução, elevando a qualidade final do software entregue.

**Referências Bibliográficas:**
1.  GOOGLE. **Android Developers: Guide to App Architecture**. Disponível em: <https://developer.android.com/jetpack/guide>. Acesso em: 29 nov. 2025.
2.  SKWRL, J.; DUDLEY, D. **Kotlin for Android Developers**. Packt Publishing, 2024.
3.  GOOGLE. **Save data in a local database using Room**. Disponível em: <https://developer.android.com/training/data-storage/room>. Acesso em: 29 nov. 2025.
