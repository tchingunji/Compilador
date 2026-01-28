# ☕ Compilador da Linguagem Java

Este repositório contém a implementação de um **compilador para a linguagem Java**, desenvolvido em **Java**, como parte da disciplina de **Compiladores**.

O projeto tem como objetivo aplicar, de forma prática, os principais conceitos estudados em sala de aula, incluindo **análise léxica, sintática e semântica**, além do **tratamento de erros**.

## 🎯 Objetivos do Projeto

- Compreender o funcionamento interno de um compilador
- Implementar as fases clássicas da compilação
- Trabalhar com gramáticas e regras da linguagem Java
- Aplicar conceitos de linguagens formais e autômatos

## ⚙️ Funcionalidades Implementadas

- ✅ Análise Léxica (tokenização do código Java)
- ✅ Análise Sintática
- ✅ Análise Semântica
- ✅ Tabela de Símbolos
- ✅ Detecção e reporte de erros léxicos, sintáticos e semânticos
- ✅ Validação de estruturas básicas da linguagem Java

> ⚠️ O compilador implementa um **subconjunto da linguagem Java**, focado nos conceitos didáticos da disciplina.

## 🛠️ Tecnologias Utilizadas

- **Java (JDK 8 ou superior)**
- IDE recomendada:
  - IntelliJ IDEA  
  - Eclipse  
  - VS Code  

## 📁 Estrutura do Projeto

📦 compilador-java
┣ 📂 src
┃ ┣ 📂 lexer # Analisador Léxico
┃ ┣ 📂 parser # Analisador Sintático
┃ ┣ 📂 semantic # Analisador Semântico
┃ ┣ 📂 symbols # Tabela de Símbolos
┃ ┗ 📜 Main.java # Classe principal
┣ 📂 examples # Exemplos de código Java
┣ 📜 README.md
┗ 📜 .gitignore

## ▶️ Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/tchingunji/compilador.git

📚 Conteúdos da Disciplina Abordados

Análise Léxica

Análise Sintática

Análise Semântica

Gramáticas Livres de Contexto

Tabela de Símbolos

Tratamento de Erros

Estrutura de um Compilador

🚧 Limitações Conhecidas

Não suporta toda a especificação da linguagem Java

Foco exclusivo em fins acadêmicos

Não há geração de bytecode JVM

📌 Possíveis Melhorias

 Suporte a mais construções da linguagem Java

 Geração de código intermediário

 Otimizações

 Interface gráfica

 Testes automatizados

👨‍🎓 Autor

Projeto desenvolvido por Seu Nome
Disciplina: Compiladores
Curso: Seu Curso
Instituição: Sua Universidade

📄 Licença

Este projeto é destinado a fins acadêmicos e educacionais.
