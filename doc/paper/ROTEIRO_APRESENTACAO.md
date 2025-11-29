# Roteiro de Apresentação - Socialização OrcaFacilApp

Este documento contém o conteúdo sugerido para a elaboração dos slides da apresentação de Socialização do projeto.

## Slide 1: Capa
- **Título do Projeto:** OrcaFacilApp - Gestão de Orçamentos com Android Jetpack
- **Nome do Aluno/Desenvolvedor:** [Seu Nome Aqui]
- **Curso/Disciplina:** Desenvolvimento Mobile / Engenharia de Software
- **Data:** [Data da Apresentação]

## Slide 2: Introdução e Problema
- **Contexto:** A dificuldade de pequenos empreendedores e autônomos em gerenciar orçamentos de forma rápida e organizada.
- **Problema:** Uso de papel ou planilhas complexas que dificultam o acesso e atualização em tempo real.
- **Solução Proposta:** Um aplicativo móvel nativo, simples e eficiente, para criar e gerenciar orçamentos diretamente do smartphone.

## Slide 3: Objetivos
- **Objetivo Geral:** Desenvolver uma aplicação Android nativa utilizando as tecnologias mais modernas recomendadas pela Google.
- **Objetivos Específicos:**
    - Implementar interface declarativa com **Jetpack Compose**.
    - Utilizar arquitetura **MVVM** para código limpo e testável.
    - Garantir persistência de dados offline com **Room Database**.

## Slide 4: Tecnologias Utilizadas
- **Linguagem:** Kotlin (Moderna, Segura e Concisa).
- **Interface:** Jetpack Compose (Sem XML, UI reativa).
- **Arquitetura:** MVVM (Model-View-ViewModel) + Repository Pattern.
- **Banco de Dados:** Room (Abstração sobre SQLite).
- **Injeção de Dependência:** Hilt/Dagger.

## Slide 5: Arquitetura do Projeto (Diagrama Simplificado)
- **Camada de UI (View):** Telas em Compose (Home, Detalhes, Cadastros).
- **Camada de ViewModel:** Gerencia o estado e regras de negócio (Cálculos de totais).
- **Camada de Dados (Repository/DAO):** Acesso ao banco de dados Room.
- *Nota: Explicar como o dado flui do banco para a tela via StateFlow.*

## Slide 6: Evidências - Interface do Usuário
- **Imagens:** Colocar prints das telas principais (Home e Detalhes).
- **Destaques:**
    - Design Limpo (Material Design 3).
    - Feedback visual (Empty States).
    - Facilidade de uso (Botões flutuantes, listas claras).

## Slide 7: Evidências - Código (Destaque Técnico)
- **Snippet 1 (Room):** Mostrar a interface `BudgetDao` para exemplificar a simplicidade das queries SQL com anotações.
- **Snippet 2 (ViewModel):** Mostrar a função de cálculo de total para exemplificar o uso de Coroutines e atualização reativa.

## Slide 8: Desafios e Aprendizados
- **Desafio:** Mudança de paradigma de XML para Compose (Declarativo).
- **Superação:** Estudo da documentação oficial e prática com componentes reutilizáveis.
- **Aprendizado:** A importância da arquitetura MVVM para manter o projeto escalável e a facilidade que o Kotlin traz para o dia a dia do desenvolvedor.

## Slide 9: Conclusão e Próximos Passos
- **Conclusão:** O OrcaFacilApp atende aos requisitos de gestão de orçamentos com alta performance e qualidade de código.
- **Próximos Passos (Futuro):**
    - Sincronização em Nuvem (Firebase).
    - Geração de PDF do orçamento para envio via WhatsApp.
    - Gráficos de faturamento mensal.

## Slide 10: Encerramento
- **Obrigado!**
- Espaço para Dúvidas e Perguntas.
