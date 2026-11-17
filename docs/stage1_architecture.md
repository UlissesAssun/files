# Technical Documentation and OOP Architecture

Academic project for an Object-Oriented Programming course.

## Class Diagram

```mermaid
classDiagram
    direction TB

    class Publication {
        <<interface>>
        +open() void
        +close() void
        +browse(int page) void
        +advancePage() void
        +backPage() void
    }

    class Person {
        -String name
        -int age
        -String gender
        +Person(String name, int age, String gender)
        +getName() String
        +setName(String name) void
        +getAge() int
        +setAge(int age) void
        +getGender() String
        +setGender(String gender) void
        +haveBirthday() void
    }

    class Book {
        -String title
        -String author
        -int totalPages
        -int currentPage
        -boolean open
        -Person reader
        +Book(String title, String author, int totalPages, Person reader)
        +details() String
        +getTitle() String
        +setTitle(String title) void
        +getAuthor() String
        +setAuthor(String author) void
        +getTotalPages() int
        +setTotalPages(int totalPages) void
        +getCurrentPage() int
        +setCurrentPage(int currentPage) void
        +getOpen() boolean
        +isOpen() boolean
        +setOpen(boolean open) void
        +getReader() Person
        +setReader(Person reader) void
        +open() void
        +close() void
        +browse(int page) void
        +advancePage() void
        +backPage() void
    }

    Book ..|> Publication : implements
    Book o-- Person : aggregation
```

## Applied OOP Concepts

1. **Abstraction:** `Person` and `Book` expose essential state and behavior.
2. **Encapsulation:** Fields are private and modified through getters and setters.
3. **Aggregation:** `Book` can hold a reference to a `Person`; both objects have independent lifecycles.
4. **Interface and polymorphism:** `Publication` defines the reading contract implemented by `Book`.
5. **State and boundaries:** A book starts closed at page zero. Browsing outside valid boundaries resets the current page to zero. Advancing and going back never exceed the page boundaries.
6. **Defensive validation:** Constructors and setters reject empty text, negative ages, zero or negative page counts, and invalid current-page values.
