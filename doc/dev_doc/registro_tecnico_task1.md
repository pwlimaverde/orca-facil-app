# Registro Técnico - Configurações Iniciais (Task 1)

Este documento registra as decisões técnicas e configurações realizadas na etapa de "Configuração Inicial e Dependências" para auxiliar na redação dos capítulos de **Metodologia** e **Fundamentação Teórica** do Paper final.

## 1. Ambiente e Ferramentas (Metodologia)

*   **Linguagem de Programação:** Kotlin 2.2.20 (Versão estável compatível com KSP 2.2.20-2.0.4).
*   **Linguagem de Script de Build:** Kotlin DSL (`build.gradle.kts`), substituindo o Groovy tradicional para melhor segurança de tipos e autocompletar no Android Studio.
*   **Versão do Java (JDK):** Java 17 (LTS), garantindo compatibilidade com as features mais recentes do Android Gradle Plugin (AGP) e bibliotecas modernas.
*   **Target SDK:** API 36 (Android 16 - Baklava), visando a compatibilidade com os dispositivos mais modernos.
*   **Min SDK:** API 28 (Android 9.0 Pie), abrangendo uma ampla base de dispositivos ativos.

## 2. Bibliotecas e Tecnologias (Fundamentação Teórica)

### Injeção de Dependência: Hilt
*   **Versão:** 2.55
*   **Justificativa:** Utilização do Dagger Hilt para injeção de dependência automatizada. Facilita a testabilidade e desacoplamento dos componentes, gerenciando o ciclo de vida das instâncias (Singletons, ViewModels, etc.) de forma eficiente no ambiente Android.
*   **Configuração:** Aplicação anotada com `@HiltAndroidApp` e Activity principal com `@AndroidEntryPoint`.

### Interface de Usuário: Jetpack Compose & Material 3
*   **Compose BOM:** 2025.10.00
*   **Material 3:** Implementação do design system mais recente do Google, garantindo acessibilidade e consistência visual.
*   **Abordagem:** UI 100% Declarativa, eliminando o uso de XML para layouts.

### Persistência de Dados: Room Database
*   **Versão:** 2.7.0
*   **Processamento de Anotações:** KSP (Kotlin Symbol Processing) versão 2.2.20-2.0.4.
*   **Justificativa:** O uso do KSP em vez do KAPT (Kotlin Annotation Processing Tool) proporciona builds mais rápidos, pois o KSP é otimizado para Kotlin e evita a geração de stubs Java intermediários.

### Navegação: Navigation Compose
*   **Versão:** 2.9.0
*   **Integração:** Uso de `hilt-navigation-compose` para injetar ViewModels diretamente nas rotas de navegação, mantendo o estado preservado durante mudanças de configuração e navegação entre telas.

## 3. Trechos de Código Relevantes para o Paper

### Configuração do Gradle (Kotlin DSL)
Pode ser citado na seção de Metodologia para demonstrar a modernização do script de build.

```kotlin
// libs.versions.toml
[versions]
kotlin = "2.2.20"
ksp = "2.2.20-2.0.4"
composeBom = "2025.10.00"

// build.gradle.kts (Project)
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false // KSP Plugin
    alias(libs.plugins.hilt) apply false // Hilt Plugin
}
```

### Aplicação Hilt
Exemplo de configuração da classe Application, fundamental para o funcionamento da injeção de dependência.

```kotlin
@HiltAndroidApp
class OrcaFacilApplication : Application()
```

## 4. Observações para o Resumo e Conclusão

*   A escolha por utilizar as versões mais recentes ("Bleeding Edge") como Kotlin 2.2.20 e Room 2.7.0 demonstra a capacidade de adaptação a novas tecnologias e a busca por performance (KSP, Coroutines otimizadas).
*   A arquitetura baseada em Hilt + Compose + Room representa o "Modern Android Development" (MAD) recomendado pelo Google em 2025.
