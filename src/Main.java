import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String CANCEL_WORD = "cancelar";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize reader and book lists
        List<Person> readers = new ArrayList<>();
        readers.add(new Person("Marcos", 21, "M"));
        readers.add(new Person("Maria", 25, "F"));

        List<Book> books = new ArrayList<>();
        books.add(new Book("POO na prática", "Allan Stieg", 300, readers.get(0)));
        books.add(new Book("Web Designer", "Marcos Pasquim", 500, readers.get(1)));
        books.add(new Book("A volta sem ir", "Jonas Confuso", 800, readers.get(0)));

        boolean running = true;

        while (running) {
            // Screen 1: book selection (Portuguese user interface)
            // ==========================================
            System.out.println("\n==================================================");
            System.out.println("              BIBLIOTECA DE LIVROS                ");
            System.out.println("==================================================");
            System.out.println("Escolha qual livro você deseja ler:\n");

            for (int i = 0; i < books.size(); i++) {
                Book b = books.get(i);
                System.out.println("[" + (i + 1) + "] " + b.getTitle());
                System.out.println("    Autor: " + b.getAuthor() + " | " + b.getTotalPages() + " páginas");
                if (b.getReader() != null) {
                    System.out.println("    Leitor: " + b.getReader().getName() + " (" + b.getReader().getAge()
                            + " anos, " + b.getReader().getGender() + ")");
                }
                System.out.println();
            }

            System.out.println("[N] Cadastrar novo livro");
            System.out.println("[0] Sair do sistema");
            System.out.println("--------------------------------------------------");
            System.out.print("Digite o número do livro ou uma opção: ");

            String choice = readLine(scanner);
            if (choice == null) {
                choice = "0";                 // End of input: stop safely
            }

            if (choice.equals("0")) {
                running = false;
                System.out.println("\nSaindo da biblioteca... Até logo!");
            } else if (choice.equalsIgnoreCase("n")) {
                registerNewBook(scanner, books, readers);
            } else {
                try {
                    int index = Integer.parseInt(choice) - 1;
                    if (index >= 0 && index < books.size()) {
                        openReadingScreen(books.get(index), scanner);
                    } else {
                        System.out.println("\n❌ Opção inválida! Escolha um número entre 1 e " + books.size() + ", N ou 0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\n❌ Entrada inválida! Digite um número, N ou 0.");
                }
            }
        }

        scanner.close();
    }

    // ==========================================
    // Screen 3: new book registration
    // ==========================================
    private static void registerNewBook(Scanner scanner, List<Book> books, List<Person> readers) {
        System.out.println("\n==================================================");
        System.out.println("               CADASTRAR NOVO LIVRO               ");
        System.out.println("==================================================");
        System.out.println("(a qualquer momento digite '" + CANCEL_WORD + "' para voltar ao menu)\n");

        String title = askText(scanner, "Título: ");
        if (title == null) { cancelled(); return; }

        String author = askText(scanner, "Autor: ");
        if (author == null) { cancelled(); return; }

        // Prevent duplicate book registrations
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title) && b.getAuthor().equalsIgnoreCase(author)) {
                System.out.println("\n❌ Esse livro já está cadastrado (" + b.getTitle() + " - " + b.getAuthor() + ").");
                return;
            }
        }

        Integer totalPages = askInt(scanner, "Total de páginas: ", 1);
        if (totalPages == null) { cancelled(); return; }

        Person reader = chooseReader(scanner, readers);
        if (reader == null) { cancelled(); return; }

        try {
            Book newBook = new Book(title, author, totalPages, reader);
            books.add(newBook);
            System.out.println("\n✅ Livro cadastrado com sucesso! Ele aparece como opção [" + books.size() + "] no menu.");
            System.out.println("   (O livro começa FECHADO e na página 0.)");
        } catch (IllegalArgumentException e) {
            System.out.println("\n❌ Não foi possível cadastrar o livro.");
        }
    }

    // Choose an existing reader or register a new one. Return null if the user cancels.
    private static Person chooseReader(Scanner scanner, List<Person> readers) {
        while (true) {
            System.out.println("\nQuem será o leitor deste livro?");
            for (int i = 0; i < readers.size(); i++) {
                Person r = readers.get(i);
                System.out.println("[" + (i + 1) + "] " + r.getName() + " (" + r.getAge() + " anos, " + r.getGender() + ")");
            }
            System.out.println("[N] Cadastrar novo leitor");
            System.out.print("Escolha: ");

            String input = readLine(scanner);
            if (input == null || input.equalsIgnoreCase(CANCEL_WORD)) {
                return null;
            }

            if (input.equalsIgnoreCase("n")) {
                Person created = registerNewReader(scanner);
                if (created == null) {
                    return null;
                }
                readers.add(created);
                return created;
            }

            try {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < readers.size()) {
                    return readers.get(index);
                }
                System.out.println("❌ Opção inválida! Escolha um número entre 1 e " + readers.size() + " ou N.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida! Digite um número ou N.");
            }
        }
    }

    private static Person registerNewReader(Scanner scanner) {
        String name = askText(scanner, "Nome do leitor: ");
        if (name == null) return null;

        Integer age = askInt(scanner, "Idade: ", 0);
        if (age == null) return null;

        while (true) {
            System.out.print("Sexo (M/F): ");
            String gender = readLine(scanner);
            if (gender == null || gender.equalsIgnoreCase(CANCEL_WORD)) return null;
            if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F")) {
                return new Person(name, age, gender.toUpperCase());
            }
            System.out.println("❌ Digite M ou F.");
        }
    }

    // ==========================================
    // Screen 2: reading screen (Portuguese user interface)
    // ==========================================
    private static void openReadingScreen(Book book, Scanner scanner) {
        if (!book.getOpen()) {
            book.open();
        }

        boolean reading = true;
        String statusMessage = "📖 Livro aberto e pronto para leitura!";

        while (reading) {
            System.out.println("\n==================================================");
            System.out.println("LIVRO: " + book.getTitle().toUpperCase());
            String readerName = book.getReader() != null ? book.getReader().getName() : "-";
            System.out.println("AUTOR: " + book.getAuthor() + " | LEITOR: " + readerName);
            System.out.println("==================================================");

            String state = book.getOpen() ? "ABERTO" : "FECHADO";
            System.out.println("ESTADO: [" + state + "]");
            System.out.println("--------------------------------------------------");
            System.out.println(">>> PÁGINA ATUAL: " + book.getCurrentPage() + " de " + book.getTotalPages() + " <<<");

            displayProgressBar(book.getCurrentPage(), book.getTotalPages());
            System.out.println("--------------------------------------------------");

            if (statusMessage != null && !statusMessage.isEmpty()) {
                System.out.println(statusMessage);
                System.out.println("--------------------------------------------------");
                statusMessage = "";
            }

            System.out.println("[1] Avançar 1 página  (->)");
            System.out.println("[2] Voltar 1 página   (<-)");
            System.out.println("[3] Folhear (ir para página específica)");
            System.out.println("[4] " + (book.getOpen() ? "Fechar livro" : "Abrir livro"));
            System.out.println("[5] Ver detalhes completos");
            System.out.println("[0] Voltar ao menu de livros");
            System.out.println("--------------------------------------------------");
            System.out.print("Escolha uma ação: ");

            String option = readLine(scanner);
            if (option == null) {
                option = "0";
            }

            switch (option) {
                case "1":
                    if (!book.getOpen()) {
                        statusMessage = "⚠️ O livro está FECHADO! Abra o livro na opção [4] para avançar páginas.";
                    } else if (book.getCurrentPage() >= book.getTotalPages()) {
                        statusMessage = "🛑 DELIMITAÇÃO: Você já está na última página (" + book.getTotalPages() + " de " + book.getTotalPages() + ")! Não é possível avançar mais.";
                    } else {
                        book.advancePage();
                        statusMessage = "⏩ Você avançou para a página " + book.getCurrentPage() + ".";
                    }
                    break;

                case "2":
                    if (!book.getOpen()) {
                        statusMessage = "⚠️ O livro está FECHADO! Abra o livro na opção [4] para voltar páginas.";
                    } else if (book.getCurrentPage() <= 0) {
                        statusMessage = "🛑 DELIMITAÇÃO: Você já está no início do livro (página 0 de " + book.getTotalPages() + ")! Não é possível voltar mais.";
                    } else {
                        book.backPage();
                        statusMessage = "⏪ Você retornou para a página " + book.getCurrentPage() + ".";
                    }
                    break;

                case "3":
                    if (!book.getOpen()) {
                        statusMessage = "⚠️ O livro está FECHADO! Abra o livro antes de folhear.";
                    } else {
                        System.out.print("Digite o número da página (0 a " + book.getTotalPages() + "): ");
                        String pageInput = readLine(scanner);
                        if (pageInput == null) {
                            break;
                        }
                        try {
                            int page = Integer.parseInt(pageInput);
                            book.browse(page);
                            if (page > book.getTotalPages()) {
                                statusMessage = "🛑 DELIMITAÇÃO (Slide 37): A página " + page + " ultrapassa o total (" + book.getTotalPages() + ")!\n   -> Regra aplicada: a página atual foi zerada para 0.";
                            } else if (page < 0) {
                                statusMessage = "🛑 DELIMITAÇÃO: Número negativo inválido! A página atual foi zerada para 0.";
                            } else {
                                statusMessage = "📖 Você folheou para a página " + page + ".";
                            }
                        } catch (NumberFormatException e) {
                            statusMessage = "❌ Entrada inválida! Digite apenas números inteiros.";
                        }
                    }
                    break;

                case "4":
                    if (book.getOpen()) {
                        book.close();
                        statusMessage = "📕 Você fechou o livro.";
                    } else {
                        book.open();
                        statusMessage = "📖 Você abriu o livro.";
                    }
                    break;

                case "5":
                    System.out.println("\n--- DETALHES DO LIVRO ---");
                    System.out.println(book.details());
                    System.out.println("Pressione ENTER para continuar lendo...");
                    readLine(scanner);
                    break;

                case "0":
                    reading = false;
                    System.out.println("\nRetornando à seleção de livros...");
                    break;

                default:
                    statusMessage = "❌ Opção inválida! Escolha uma das opções disponíveis.";
                    break;
            }
        }
    }

    // ==========================================
    // INPUT UTILITIES
    // ==========================================

    // Read a line; return null when input has ended.
    private static String readLine(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine().trim();
    }

    // Ask for non-empty text until a valid value is provided. Return null if cancelled.
    private static String askText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = readLine(scanner);
            if (input == null || input.equalsIgnoreCase(CANCEL_WORD)) {
                return null;
            }
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("❌ Este campo não pode ficar vazio.");
        }
    }

    // Ask for an integer greater than or equal to min until it is valid. Return null if cancelled.
    private static Integer askInt(Scanner scanner, String prompt, int min) {
        while (true) {
            System.out.print(prompt);
            String input = readLine(scanner);
            if (input == null || input.equalsIgnoreCase(CANCEL_WORD)) {
                return null;
            }
            try {
                int value = Integer.parseInt(input);
                if (value >= min) {
                    return value;
                }
                System.out.println("❌ O valor deve ser maior ou igual a " + min + ".");
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida! Digite apenas números inteiros.");
            }
        }
    }

    private static void cancelled() {
        System.out.println("\nCadastro cancelado. Voltando ao menu...");
    }

    private static void displayProgressBar(int current, int total) {
        int barLength = 20;
        int filled = total > 0 ? (int) (((double) current / total) * barLength) : 0;
        if (filled > barLength) filled = barLength;

        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < filled) {
                bar.append("=");
            } else if (i == filled) {
                bar.append(">");
            } else {
                bar.append(" ");
            }
        }
        bar.append("]");

        double percentage = total > 0 ? ((double) current / total) * 100 : 0.0;
        System.out.printf("Progresso: %s %.1f%%\n", bar.toString(), percentage);
    }
}
