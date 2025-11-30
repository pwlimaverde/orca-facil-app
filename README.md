# OrcaFacilApp 📱💰

**OrcaFacilApp** é uma aplicação Android nativa moderna projetada para simplificar a criação e o gerenciamento de orçamentos para profissionais autônomos e pequenas empresas.

Desenvolvido inteiramente em **Kotlin** e utilizando as práticas mais recentes do **Modern Android Development (MAD)**, o projeto serve tanto como uma ferramenta utilitária robusta quanto como um modelo de referência para arquiteturas de software contemporâneas.

---

## 📸 Screenshots

| Tela Inicial (Home) | Detalhes do Orçamento |
|:-------------------:|:---------------------:|
| ![Home](doc/evidencias/tela_home.png) | ![Detalhes](doc/evidencias/tela_detalhes_orcamento.png) |

| Cadastro de Orçamento | Cadastro de Item |
|:---------------------:|:----------------:|
| ![Novo Orçamento](doc/evidencias/tela_cadastro_orcamento.png) | ![Novo Item](doc/evidencias/tela_cadastro_item.png) |

---

## 🚀 Funcionalidades

*   **Gestão de Orçamentos:** Crie, visualize e exclua orçamentos de forma intuitiva.
*   **Itens Detalhados:** Adicione múltiplos itens (produtos ou serviços) a cada orçamento.
*   **Cálculo Automático:** O valor total do orçamento é atualizado em tempo real conforme itens são adicionados ou modificados.
*   **Interface Moderna:** Design limpo e responsivo seguindo o **Material Design 3**.
*   **Tema Dinâmico:** Suporte a **Dynamic Colors** (Android 12+) e modos Claro/Escuro.
*   **Persistência Local:** Todos os dados são salvos localmente no dispositivo, funcionando offline.

---

## 🛠️ Stack Tecnológico

O projeto foi construído utilizando as tecnologias e bibliotecas mais recentes do ecossistema Android:

*   **Linguagem:** [Kotlin](https://kotlinlang.org/) (v2.2.20)
*   **Interface de Usuário:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
    *   Material Design 3 (Material You)
    *   Navigation Compose
*   **Arquitetura:** MVVM (Model-View-ViewModel)
*   **Injeção de Dependência:** [Dagger Hilt](https://dagger.dev/hilt/)
*   **Banco de Dados:** [Room Database](https://developer.android.com/training/data-storage/room) (v2.7.0)
    *   SQLite Abstraction
    *   KSP (Kotlin Symbol Processing)
*   **Assincronismo:** Kotlin Coroutines & Flow
*   **Build System:** Gradle com Kotlin DSL (`build.gradle.kts`) e Version Catalog (`libs.versions.toml`)

---

## 🏗️ Arquitetura

O projeto segue os princípios da **Clean Architecture** e **MVVM**, garantindo separação de responsabilidades e testabilidade:

*   **data/**: Contém a implementação do Banco de Dados (Room), DAOs e Repositórios.
    *   `repository/`: Single Source of Truth (SSOT) para os dados.
*   **di/**: Módulos do Dagger Hilt para injeção de dependências.
*   **ui/**: Camada de apresentação.
    *   `viewmodel/`: Gerenciamento de estado e lógica de apresentação.
    *   `screen/`: Telas construídas com Composable functions.
    *   `components/`: Componentes de UI reutilizáveis.
    *   `theme/`: Definições de tema, cores e tipografia.

---

## 💻 Como Rodar o Projeto

### Pré-requisitos
*   Android Studio Ladybug (ou superior)
*   JDK 17
*   Dispositivo ou Emulador Android (Recomendado API 31+ para cores dinâmicas, Min API 28)

### Passos
1.  Clone este repositório:
    ```bash
    git clone https://github.com/seu-usuario/OrcaFacilApp.git
    ```
2.  Abra o projeto no **Android Studio**.
3.  Aguarde a sincronização do Gradle.
4.  Execute a aplicação (`Shift + F10`) no emulador ou dispositivo físico.

---

## 📄 Licença

Este projeto está sob a licença [MIT](LICENSE).

---

Desenvolvido como parte de um estudo aprofundado sobre Modern Android Development.
