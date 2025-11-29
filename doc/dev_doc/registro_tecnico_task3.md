# Registro Técnico - Modelagem de Dados com Room (Task 3)

Este documento detalha a estrutura do banco de dados local implementada na etapa de "Modelagem de Dados", servindo como base para a seção de **Arquitetura de Software** e **Implementação do Banco de Dados** do Paper.

## 1. Esquema Relacional (Metodologia)

O banco de dados foi modelado para persistir orçamentos e seus respectivos itens, configurando uma relação clássica de **Um-para-Muitos (1:N)**.

### Entidade: Budget (Orçamento)
Representa a tabela principal `budgets`.
*   **id:** Chave primária (PK) autoincremental (`Long`).
*   **nome:** Nome do cliente ou título do orçamento (`String`).
*   **descricao:** Detalhes adicionais do serviço/projeto (`String`).
*   **data_criacao:** Timestamp de criação (`Long`), inicializado automaticamente com `System.currentTimeMillis()`.

### Entidade: BudgetItem (Item de Orçamento)
Representa a tabela `budget_items`, contendo os produtos ou serviços associados a um orçamento.
*   **id:** Chave primária (PK) autoincremental (`Long`).
*   **budget_id:** Chave estrangeira (FK) referenciando `budgets.id`.
*   **nome_produto:** Descrição do item (`String`).
*   **valor_unitario:** Preço unitário (`Double`).
*   **quantidade:** Quantidade do item (`Int`).

### Integridade Referencial (Fundamentação Teórica)
A chave estrangeira em `BudgetItem` foi configurada com **`onDelete = ForeignKey.CASCADE`**.
*   **Comportamento:** Isso garante que, ao excluir um Orçamento (`Budget`), todos os seus Itens (`BudgetItem`) sejam automaticamente removidos pelo banco de dados, evitando registros órfãos e mantendo a consistência dos dados sem necessidade de lógica manual adicional.

## 2. Mapeamento Objeto-Relacional (ORM)

Utilizamos a biblioteca **Room** para abstrair o SQL. Abaixo estão os destaques da implementação.

### Definição da Chave Estrangeira e Índices
Para otimizar consultas e garantir a integridade, a tabela `budget_items` possui um índice na coluna `budget_id`.

```kotlin
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
    indices = [Index(value = ["budget_id"])] // Índice para performance em JOINs
)
data class BudgetItem(...)
```

### Relação 1:N (One-to-Many)
Para recuperar um Orçamento com todos os seus itens em uma única consulta, foi criada a classe de dados `BudgetWithItems`. O Room utiliza as anotações `@Embedded` e `@Relation` para preencher automaticamente essa estrutura.

```kotlin
data class BudgetWithItems(
    @Embedded val budget: Budget,
    @Relation(
        parentColumn = "id",
        entityColumn = "budget_id"
    )
    val items: List<BudgetItem>
)
```

## 3. Próximos Passos (Arquitetura)
Com as entidades definidas, a próxima etapa (Task 4) envolverá a criação dos **Data Access Objects (DAOs)** para definir as operações de CRUD (Create, Read, Update, Delete) e a configuração da classe abstrata `AppDatabase` para inicializar o Room.
