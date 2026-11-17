# Object-Oriented Programming (OOP) in Java - Practical Project

Academic project for an **Object-Oriented Programming** course.
Professor: **Állan Stieg**

The application models the interaction between a person/reader (`Person`) and a publication (`Book`) through the `Publication` interface.

## Project Structure

```text
src/
├── Person.java           # Person entity
├── Book.java             # Book entity and reader aggregation
├── Publication.java      # Publication contract
├── Main.java             # Automated test suite
└── TerminalInterface.java  # Interactive Portuguese console interface
docs/
└── stage1_architecture.md
README.md
```

## Implemented OOP Concepts

- **Abstraction:** Essential attributes and behaviors for `Person` and `Book`.
- **Encapsulation:** Private fields accessed through getters and setters.
- **Aggregation:** `Book` stores a reference to a `Person`.
- **Interfaces and polymorphism:** `Publication` defines the reading operations implemented by `Book`.
- **State and boundaries:** Books start closed at page zero, and navigation respects page limits.

## Compile and Run

Compile the Java source files:

```powershell
cd src
javac -encoding UTF-8 *.java
```

Run the automated test suite:

```powershell
java Main
```

Run the interactive terminal interface:

```powershell
java TerminalInterface
```

You can also launch the interface through `Main`:

```powershell
java Main --interactive
```
