# Registro Técnico - Task 6: Camada de Apresentação (ViewModels)

## Objetivo
Implementar a camada de apresentação utilizando ViewModels e StateFlow para gerenciar o estado da UI e interagir com a camada de repositório, garantindo a separação de responsabilidades e a reatividade.

## Alterações Realizadas

### 1. HomeViewModel
- Responsável pela tela principal de listagem de orçamentos.
- Injeta `OrcaRepository` via Hilt.
- Expõe `budgets` como um `StateFlow<List<Budget>>` que observa as mudanças no banco de dados em tempo real.
- Implementa métodos para adicionar e remover orçamentos.

### 2. BudgetDetailViewModel
- Responsável pela tela de detalhes e edição de um orçamento.
- Recebe o `budgetId` através de `SavedStateHandle` (argumentos de navegação).
- Expõe `budgetWithItems` como `StateFlow<BudgetWithItems?>` para mostrar os dados do orçamento e seus itens.
- Gerencia a lógica de adicionar, editar e remover itens do orçamento.
- **Cálculo Automático**: Implementada lógica para recalcular o `valorTotal` do orçamento sempre que itens são modificados, garantindo consistência nos dados.

## Estrutura de Arquivos
- `app/src/main/java/com/pwlimaverde/orcafacilapp/ui/viewmodel/HomeViewModel.kt`
- `app/src/main/java/com/pwlimaverde/orcafacilapp/ui/viewmodel/BudgetDetailViewModel.kt`

## Tecnologias Utilizadas
- **Jetpack ViewModel**: Para manter o estado da UI sobrevivendo a mudanças de configuração.
- **StateFlow**: Para fluxos de dados observáveis e reativos compatíveis com Jetpack Compose.
- **SavedStateHandle**: Para recuperar argumentos de navegação de forma segura.
- **HiltViewModel**: Para injeção de dependência nos ViewModels.

## Próximos Passos
- Implementar a UI com Jetpack Compose (Task 7), conectando as telas aos ViewModels criados.
