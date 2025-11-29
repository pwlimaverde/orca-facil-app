# Registro Técnico - Design System e Tema (Task 2)

Este documento registra as decisões de design e implementações técnicas realizadas na etapa de "Implementação do Design System e Tema", fornecendo subsídios para os capítulos de **Interface de Usuário** e **Experiência do Usuário (UX)** do Paper final.

## 1. Estratégia de Design (Metodologia)

*   **Padrão Visual:** Material Design 3 (Material You).
*   **Justificativa:** Adoção do padrão mais recente do Google para garantir consistência visual, suporte nativo a componentes modernos e melhor acessibilidade.
*   **Abordagem de Cores:**
    *   **Dynamic Color (Cores Dinâmicas):** Ativado para dispositivos com Android 12+ (API 31+), permitindo que o app se adapte ao papel de parede do usuário, aumentando a personalização e integração com o sistema operacional.
    *   **Tema Personalizado (Fallback):** Criação de uma identidade visual "Ocean Blue" (Azul Oceano) para dispositivos anteriores ao Android 12 ou quando as cores dinâmicas não estão disponíveis.
*   **Modos de Exibição:** Suporte completo e nativo para **Light Mode** (Claro) e **Dark Mode** (Escuro).

## 2. Componentes do Tema (Fundamentação Teórica)

### Paleta de Cores (`Color.kt`)
A paleta foi estruturada semanticamente seguindo as roles do Material 3, garantindo contraste adequado e hierarquia visual.

*   **Primary:** Azul profundo (`#0061A4`) para ações principais e destaque.
*   **Secondary:** Tons de cinza-azulado (`#535F70`) para elementos de menor ênfase.
*   **Tertiary:** Roxo suave (`#6B5778`) para acentos visuais e diferenciação.
*   **Error:** Vermelho padrão (`#BA1A1A`) para estados de alerta.
*   **Surface/Background:** Tons neutros adaptativos para garantir legibilidade do texto.

### Tipografia (`Type.kt`)
Implementação da escala tipográfica completa do Material 3, definindo tamanhos, pesos e espaçamentos para diferentes contextos de leitura:
*   **Display & Headline:** Para títulos de grande impacto.
*   **Title:** Para seções e cabeçalhos de cartões.
*   **Body:** Para o conteúdo principal (leitura longa).
*   **Label:** Para botões e legendas pequenas.

### Tema e Edge-to-Edge (`Theme.kt`)
O tema foi configurado para suportar a experiência **Edge-to-Edge**, onde o conteúdo do aplicativo se estende por trás das barras de sistema (status bar e navigation bar), oferecendo uma visualização mais imersiva.

## 3. Desafios Técnicos e Soluções

### Depreciação da API de Status Bar
Durante a implementação, identificou-se que a definição direta de `window.statusBarColor` está depreciada e conflitava com a abordagem Edge-to-Edge moderna.

*   **Problema:** O código gerado inicialmente tentava definir uma cor sólida para a barra de status, o que não é recomendado para designs imersivos modernos.
*   **Solução:** A definição de cor foi removida, mantendo a barra de status transparente. O controle `WindowCompat.getInsetsController(...).isAppearanceLightStatusBars` foi ajustado para garantir que os ícones da barra de status (relógio, bateria) tenham a cor correta (preto ou branco) dependendo se o fundo é claro ou escuro.

```kotlin
// Solução aplicada em Theme.kt
val view = LocalView.current
if (!view.isInEditMode) {
    SideEffect {
        val window = (view.context as Activity).window
        // window.statusBarColor removido para suporte Edge-to-Edge
        
        // Ajuste de contraste dos ícones
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
    }
}
```

## 4. Trechos de Código Relevantes para o Paper

### Lógica de Seleção de Cores (Dynamic vs Custom)
Este trecho demonstra a verificação de versão do Android para aplicar a melhor experiência possível ao usuário.

```kotlin
val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }
    darkTheme -> DarkColorScheme // Esquema personalizado Escuro
    else -> LightColorScheme // Esquema personalizado Claro
}
```

## 5. Conclusão da Etapa
A implementação do Design System estabelece a fundação visual do aplicativo OrcaFácil. A adesão estrita ao Material 3 não apenas moderniza a interface, mas também simplifica o desenvolvimento futuro de telas, pois os componentes do Jetpack Compose herdarão automaticamente essas definições de estilo.
