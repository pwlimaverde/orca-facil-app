# Registro Técnico - Task 5: Camada de Repositório

## Objetivo
Implementar o padrão Repository para abstrair o acesso aos dados, servindo como uma única fonte de verdade para a UI e ViewModels, seguindo os princípios da Clean Architecture.

## Alterações Realizadas

### 1. Definição da Interface do Repositório
- Criada a interface `OrcaRepository` em `data/repository`.
- Define contratos para operações CRUD em `Budget` e `BudgetItem`.
- Utiliza `Flow` para operações de leitura reativas e `suspend functions` para escrita.

### 2. Implementação do Repositório
- Implementada a classe `OrcaRepositoryImpl` que concretiza `OrcaRepository`.
- Injeta `BudgetDao` e `BudgetItemDao` via construtor (Injeção de Dependência).
- Delega as operações para os DAOs correspondentes.

### 3. Injeção de Dependência (Hilt)
- Criado o módulo `RepositoryModule` em `di`.
- Utiliza `@Binds` para associar a interface `OrcaRepository` à sua implementação `OrcaRepositoryImpl`.
- Escopo definido como `@Singleton` para garantir uma única instância do repositório em toda a aplicação.

## Estrutura de Arquivos
- `app/src/main/java/com/pwlimaverde/orcafacilapp/data/repository/OrcaRepository.kt`
- `app/src/main/java/com/pwlimaverde/orcafacilapp/data/repository/OrcaRepositoryImpl.kt`
- `app/src/main/java/com/pwlimaverde/orcafacilapp/di/RepositoryModule.kt`

## Tecnologias Utilizadas
- **Kotlin Coroutines & Flow**: Para assincronismo e fluxos de dados reativos.
- **Dagger Hilt**: Para injeção de dependência e gerenciamento do ciclo de vida dos componentes.
- **Repository Pattern**: Para desacoplamento entre a camada de dados e a camada de apresentação.

## Próximos Passos
- Implementar os ViewModels para consumir o Repositório (Task 6).
