# Programação Orientada a Objetos (POO) em Java — Projeto Prático

Projeto acadêmico desenvolvido para a disciplina de **Programação Orientada a Objetos (POO)**.
Professor: **Állan Stieg**

A aplicação modela e implementa a interação entre uma pessoa/leitor (`Pessoa`) e uma obra literária (`Livro`), estabelecendo um contrato de interface com `Publicacao`. O código-fonte segue fielmente a modelagem dos slides da disciplina (Slides 1 a 43).

---

## 🏛️ Estrutura do Projeto

```text
.
├── Pessoa.java               # Modelo da entidade Pessoa (leitor)
├── Livro.java                # Modelo da entidade Livro (agrega Pessoa e implementa Publicacao)
├── Publicacao.java           # Interface com os métodos abstratos de publicação (Slide 25)
├── Main.java                 # Suíte de testes integrados (Slides 33 a 38)
├── TerminalInterface.java    # Interface interativa de console (leitura e cadastro)
├── stage1_architecture.md    # Especificação arquitetural e Diagrama de Classes UML
└── README.md                 # Documentação do projeto
```

---

## 🧩 Conceitos de POO Implementados

* **Abstração:** Modelagem de atributos essenciais para `Pessoa` (`nome`, `idade`, `sexo`) e `Livro` (`titulo`, `autor`, `totPaginas`, `pagAtual`, `aberto`, `leitor`).
* **Encapsulamento Estrito:** Atributos privados acessados e modificados exclusivamente por Getters e Setters (`getNome`, `setNome`, `getTitulo`, `setTitulo`, `getTotPaginas`, `getPagAtual`, `getAberto`, etc.).
* **Agregação:** A classe `Livro` possui uma referência a `Pessoa` como seu `leitor`. Ambas as instâncias possuem ciclos de vida independentes.
* **Interface e Polimorfismo:** `Publicacao` declara `abrir()`, `fechar()`, `folhear(int p)`, `avancarPag()` e `voltarPag()`, implementados com `@Override` em `Livro`.
* **Estados e Limites (Slides 32 a 38):**
  - Estado inicial: livro criado fechado (`aberto = false`) e na página zero (`pagAtual = 0`).
  - Tentativa de folhear além da capacidade ($p > totPaginas$) redefine `pagAtual` para `0` (Slide 37).
  - `avancarPag()` e `voltarPag()` respeitam os limites de páginas do livro.
  - Saída exata de `detalhes()` conforme o Slide 35 (`Livro:Título= ...`).

---

## ➕ Funcionalidade Adicional: Cadastro Interativo de Livros

Na interface de terminal (`TerminalInterface`), a opção **[N] Cadastrar novo livro**:

1. Solicita título, autor e total de páginas (com validação de dados).
2. Permite associar um leitor existente ou cadastrar um novo leitor (`Pessoa`).
3. Bloqueia duplicações de livros com mesmo título e autor.
4. Cria o `Livro` iniciando fechado na página 0, pronto para ser lido no menu interativo.

---

## 🚀 Como Compilar e Executar

### 1. Compilar os Arquivos Java
```powershell
javac -encoding UTF-8 *.java
```

### 2. Executar os Testes Automatizados (Slides 33 a 38)
```powershell
java Main
```

### 3. Executar o Leitor Interativo no Terminal
```powershell
java TerminalInterface
```
*(ou `java Main --interactive`)*
