**RESUMO**

Este trabalho apresenta o desenvolvimento do aplicativo **OrcaFacilApp**, uma solução móvel nativa para a plataforma Android destinada à criação e gestão simplificada de orçamentos. O objetivo principal foi aplicar e demonstrar a eficácia das tecnologias modernas recomendadas pela Google, utilizando a linguagem **Kotlin** como base, o **Jetpack Compose** para a construção de interfaces declarativas e a biblioteca **Room** para a persistência de dados local. A metodologia adotada seguiu os princípios da Engenharia de Software, com uma arquitetura baseada no padrão **MVVM (Model-View-ViewModel)**, garantindo a separação de responsabilidades e a testabilidade do código. Os resultados obtidos incluem uma aplicação funcional, capaz de cadastrar orçamentos e itens, calcular totais automaticamente e manter os dados salvos no dispositivo. O projeto evidencia como a adoção de ferramentas modernas reduz a complexidade do desenvolvimento e eleva a qualidade do software final, proporcionando uma experiência de usuário fluida e segura.

**Palavras-chave:** Desenvolvimento Android. Kotlin. Jetpack Compose. Room Database. Gestão de Orçamentos.

---

# 1. Introdução

A gestão financeira pessoal e empresarial é um desafio constante, demandando ferramentas que ofereçam agilidade e precisão no controle de gastos e orçamentos. Com a onipresença dos dispositivos móveis, aplicativos de gestão tornaram-se aliados indispensáveis para profissionais autônomos e pequenas empresas que necessitam criar orçamentos de forma rápida e organizada.

O presente trabalho tem como objetivo descrever o desenvolvimento do **OrcaFacilApp**, um aplicativo nativo para o sistema operacional Android, projetado para facilitar a criação e gestão de orçamentos. O projeto foi concebido com o intuito de explorar e aplicar as tecnologias mais recentes recomendadas pela Google para o desenvolvimento mobile moderno, especificamente a linguagem **Kotlin**, o kit de interface **Jetpack Compose** e a biblioteca de persistência **Room**.

A escolha dessas tecnologias justifica-se pela necessidade de construir uma solução robusta, escalável e de fácil manutenção. O Kotlin oferece segurança e concisão; o Jetpack Compose revoluciona a construção de interfaces com sua abordagem declarativa; e o Room garante a integridade dos dados locais. Ao longo deste documento, serão detalhados os aspectos teóricos que fundamentam essas escolhas, a metodologia de desenvolvimento aplicada e os resultados obtidos, evidenciados através de trechos de código e capturas de tela da aplicação funcional.

---

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

---

# 3. Metodologia

A metodologia adotada para o desenvolvimento do **OrcaFacilApp** baseou-se nas práticas recomendadas de Engenharia de Software Moderna, com foco específico no desenvolvimento nativo para a plataforma Android. O processo foi estruturado em etapas incrementais, abrangendo desde a configuração do ambiente até a implementação da lógica de negócios e interface do usuário.

## 3.1 Ferramentas e Ambiente de Desenvolvimento

O projeto foi desenvolvido utilizando o **Android Studio Ladybug**, a IDE oficial da Google para desenvolvimento Android. O sistema de build utilizado foi o Gradle, configurado através da linguagem **Kotlin DSL** (`build.gradle.kts`), que oferece melhor autocompletar e verificação de erros nos scripts de configuração em comparação com o Groovy tradicional. A linguagem de programação escolhida foi o **Kotlin**, visando compatibilidade total com as bibliotecas modernas do Android Jetpack.

Para o controle de versão, utilizou-se o **Git**, seguindo um fluxo de trabalho simplificado baseado em *feature branches*, onde cada nova funcionalidade (como a criação de telas ou lógica de banco de dados) foi desenvolvida em um ramo isolado antes de ser integrada ao ramo principal.

## 3.2 Arquitetura da Solução

A arquitetura do aplicativo segue o padrão **MVVM (Model-View-ViewModel)**, recomendado pela Google para garantir a separação de interesses e facilitar testes automatizados.

1.  **Model (Camada de Dados):** Responsável pela persistência e recuperação de dados. Utilizou-se a biblioteca **Room** para criar um banco de dados SQLite local. Foram definidas duas entidades principais: `Budget` (Orçamento) e `BudgetItem` (Itens do Orçamento), com um relacionamento de um-para-muitos.
2.  **Repository:** Atua como uma fonte única da verdade, mediando o acesso aos dados entre o banco de dados (DAO) e a camada de apresentação.
3.  **ViewModel:** Gerencia o estado da interface do usuário e executa a lógica de negócios. Os ViewModels (`HomeViewModel` e `BudgetDetailViewModel`) expõem dados observáveis através de `StateFlow`, garantindo que a UI reaja automaticamente a mudanças nos dados.
4.  **View (Interface de Usuário):** Construída inteiramente com **Jetpack Compose**, eliminando o uso de arquivos XML de layout. A UI é composta por funções *Composable* modulares e reutilizáveis.

## 3.3 Fluxo de Funcionamento e Telas

O fluxo do aplicativo foi desenhado para ser intuitivo e direto. Abaixo, apresentamos as principais telas desenvolvidas e suas funcionalidades.

### 3.3.1 Tela Principal (Home)

A tela inicial apresenta a listagem de todos os orçamentos cadastrados no sistema, ordenados pela data de criação. Caso não existam orçamentos, um "Empty State" amigável orienta o usuário a criar o primeiro registro.

![Figura 1: Tela Principal do OrcaFacilApp](../evidencias/tela_home.png)
*Figura 1: Tela Principal do OrcaFacilApp exibindo a lista de orçamentos.*

### 3.3.2 Cadastro de Orçamento

Ao acionar o botão de ação flutuante (FAB) na tela principal, o usuário é apresentado a um diálogo para inserir o nome e a descrição do novo orçamento. A validação básica impede a criação de orçamentos sem nome.

![Figura 2: Formulário para criação de um novo orçamento](../evidencias/tela_cadastro_orcamento.png)
*Figura 2: Formulário para criação de um novo orçamento.*

### 3.3.3 Detalhes e Adição de Itens

Ao selecionar um orçamento, o usuário é direcionado para a tela de detalhes. Nesta tela, é possível visualizar o valor total acumulado e a lista de itens. Um novo botão de ação permite adicionar produtos ou serviços ao orçamento, especificando nome, quantidade e valor unitário. O sistema recalcula o total automaticamente após cada inserção.

![Figura 3: Tela de detalhes exibindo itens e valor total calculado](../evidencias/tela_detalhes_orcamento.png)
*Figura 3: Tela de detalhes exibindo itens e valor total calculado.*

![Figura 4: Interface para inserção de novos itens ao orçamento](../evidencias/tela_cadastro_item.png)
*Figura 4: Interface para inserção de novos itens ao orçamento.*

## 3.4 Testes e Validação

Durante o desenvolvimento, foram realizados testes manuais exploratórios para validar cada funcionalidade implementada. O uso de injeção de dependência com **Hilt** facilitou a estruturação do código, preparando o terreno para futuros testes unitários automatizados. A validação final consistiu na verificação do fluxo completo: criação de orçamento -> adição de itens -> persistência dos dados -> reinicialização do app para confirmar que os dados foram salvos corretamente no banco de dados SQLite.

---

# 4. Considerações Finais

O desenvolvimento do **OrcaFacilApp** proporcionou uma experiência prática aprofundada no ecossistema de desenvolvimento Android moderno. A transição do paradigma imperativo (XML) para o declarativo (Jetpack Compose) representou inicialmente um desafio de aprendizado, exigindo uma mudança na forma de pensar a construção de interfaces. No entanto, os benefícios tornaram-se evidentes rapidamente: a redução da quantidade de código, o encapsulamento de componentes e a facilidade de criar layouts dinâmicos e responsivos.

A implementação da arquitetura MVVM, aliada à injeção de dependência com Hilt, provou ser essencial para manter o código organizado e testável. A separação clara entre a lógica de negócios (ViewModel) e a interface (Compose) permitiu que alterações no design fossem feitas sem impactar as regras de funcionamento do aplicativo. O uso do Room para persistência de dados demonstrou ser uma solução robusta, eliminando a complexidade do gerenciamento direto de conexões SQL e cursores.

Conclui-se que a utilização da stack tecnológica Kotlin + Jetpack Compose + Room não apenas atende aos requisitos funcionais de um aplicativo de gestão de orçamentos, mas também entrega um produto de software de alta qualidade técnica. Como trabalhos futuros, sugere-se a implementação de sincronização em nuvem (utilizando Firebase ou API REST) e a adição de relatórios gráficos para análise de orçamentos, expandindo ainda mais a utilidade da ferramenta para o usuário final.
