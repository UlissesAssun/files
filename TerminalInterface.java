import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TerminalInterface {

    private static final String CANCEL_WORD = "cancelar";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inicialização de leitores e livros (listas, para permitir cadastro de novos itens)
        List<Pessoa> leitores = new ArrayList<>();
        leitores.add(new Pessoa("Marcos", 21, "M"));
        leitores.add(new Pessoa("Maria", 25, "F"));

        List<Livro> livros = new ArrayList<>();
        livros.add(new Livro("POO na prática", "Allan Stieg", 300, leitores.get(0)));
        livros.add(new Livro("Web Designer", "Marcos Pasquim", 500, leitores.get(1)));
        livros.add(new Livro("A volta sem ir", "Jonas Confuso", 800, leitores.get(0)));

        boolean running = true;

        while (running) {
            // ==========================================
            // TELA 1: SELEÇÃO DE LIVROS (INTERFACE EM PORTUGUÊS)
            // ==========================================
            System.out.println("\n==================================================");
            System.out.println("              BIBLIOTECA DE LIVROS                ");
            System.out.println("==================================================");
            System.out.println("Escolha qual livro você deseja ler:\n");

            for (int i = 0; i < livros.size(); i++) {
                Livro b = livros.get(i);
                System.out.println("[" + (i + 1) + "] " + b.getTitulo());
                System.out.println("    Autor: " + b.getAutor() + " | " + b.getTotPaginas() + " páginas");
                if (b.getLeitor() != null) {
                    System.out.println("    Leitor: " + b.getLeitor().getNome() + " (" + b.getLeitor().getIdade()
                            + " anos, " + b.getLeitor().getSexo() + ")");
                }
                System.out.println();
            }

            System.out.println("[N] Cadastrar novo livro");
            System.out.println("[0] Sair do sistema");
            System.out.println("--------------------------------------------------");
            System.out.print("Digite o número do livro ou uma opção: ");

            String choice = readLine(scanner);
            if (choice == null) {
                choice = "0"; // fim da entrada: encerra com segurança
            }

            if (choice.equals("0")) {
                running = false;
                System.out.println("\nSaindo da biblioteca... Até logo!");
            } else if (choice.equalsIgnoreCase("n")) {
                registerNewBook(scanner, livros, leitores);
            } else {
                try {
                    int index = Integer.parseInt(choice) - 1;
                    if (index >= 0 && index < livros.size()) {
                        openReadingScreen(livros.get(index), scanner);
                    } else {
                        System.out.println("\n❌ Opção inválida! Escolha um número entre 1 e " + livros.size() + ", N ou 0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\n❌ Entrada inválida! Digite um número, N ou 0.");
                }
            }
        }

        scanner.close();
    }

    // ==========================================
    // TELA 3: CADASTRO DE NOVO LIVRO
    // ==========================================
    private static void registerNewBook(Scanner scanner, List<Livro> livros, List<Pessoa> leitores) {
        System.out.println("\n==================================================");
        System.out.println("               CADASTRAR NOVO LIVRO               ");
        System.out.println("==================================================");
        System.out.println("(a qualquer momento digite '" + CANCEL_WORD + "' para voltar ao menu)\n");

        String titulo = askText(scanner, "Título: ");
        if (titulo == null) { cancelled(); return; }

        String autor = askText(scanner, "Autor: ");
        if (autor == null) { cancelled(); return; }

        // Evita cadastrar o mesmo livro duas vezes
        for (Livro b : livros) {
            if (b.getTitulo().equalsIgnoreCase(titulo) && b.getAutor().equalsIgnoreCase(autor)) {
                System.out.println("\n❌ Esse livro já está cadastrado (" + b.getTitulo() + " - " + b.getAutor() + ").");
                return;
            }
        }

        Integer totPaginas = askInt(scanner, "Total de páginas: ", 1);
        if (totPaginas == null) { cancelled(); return; }

        Pessoa leitor = chooseReader(scanner, leitores);
        if (leitor == null) { cancelled(); return; }

        try {
            Livro novoLivro = new Livro(titulo, autor, totPaginas, leitor);
            livros.add(novoLivro);
            System.out.println("\n✅ Livro cadastrado com sucesso! Ele aparece como opção [" + livros.size() + "] no menu.");
            System.out.println("   (O livro começa FECHADO e na página 0.)");
        } catch (IllegalArgumentException e) {
            System.out.println("\n❌ Não foi possível cadastrar o livro: " + e.getMessage());
        }
    }

    // Escolhe um leitor existente ou cadastra um novo. Retorna null se o usuário cancelar.
    private static Pessoa chooseReader(Scanner scanner, List<Pessoa> leitores) {
        while (true) {
            System.out.println("\nQuem será o leitor deste livro?");
            for (int i = 0; i < leitores.size(); i++) {
                Pessoa r = leitores.get(i);
                System.out.println("[" + (i + 1) + "] " + r.getNome() + " (" + r.getIdade() + " anos, " + r.getSexo() + ")");
            }
            System.out.println("[N] Cadastrar novo leitor");
            System.out.print("Escolha: ");

            String input = readLine(scanner);
            if (input == null || input.equalsIgnoreCase(CANCEL_WORD)) {
                return null;
            }

            if (input.equalsIgnoreCase("n")) {
                Pessoa created = registerNewReader(scanner);
                if (created == null) {
                    return null;
                }
                leitores.add(created);
                return created;
            }

            try {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < leitores.size()) {
                    return leitores.get(index);
                }
                System.out.println("❌ Opção inválida! Escolha um número entre 1 e " + leitores.size() + " ou N.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida! Digite um número ou N.");
            }
        }
    }

    private static Pessoa registerNewReader(Scanner scanner) {
        String nome = askText(scanner, "Nome do leitor: ");
        if (nome == null) return null;

        Integer idade = askInt(scanner, "Idade: ", 0);
        if (idade == null) return null;

        while (true) {
            System.out.print("Sexo (M/F): ");
            String sexo = readLine(scanner);
            if (sexo == null || sexo.equalsIgnoreCase(CANCEL_WORD)) return null;
            if (sexo.equalsIgnoreCase("M") || sexo.equalsIgnoreCase("F")) {
                return new Pessoa(nome, idade, sexo.toUpperCase());
            }
            System.out.println("❌ Digite M ou F.");
        }
    }

    // ==========================================
    // TELA 2: TELA DE LEITURA (INTERFACE EM PORTUGUÊS)
    // ==========================================
    private static void openReadingScreen(Livro livro, Scanner scanner) {
        if (!livro.getAberto()) {
            livro.abrir();
        }

        boolean reading = true;
        String statusMessage = "📖 Livro aberto e pronto para leitura!";

        while (reading) {
            System.out.println("\n==================================================");
            System.out.println("LIVRO: " + livro.getTitulo().toUpperCase());
            String readerName = livro.getLeitor() != null ? livro.getLeitor().getNome() : "-";
            System.out.println("AUTOR: " + livro.getAutor() + " | LEITOR: " + readerName);
            System.out.println("==================================================");

            String state = livro.getAberto() ? "ABERTO" : "FECHADO";
            System.out.println("ESTADO: [" + state + "]");
            System.out.println("--------------------------------------------------");
            System.out.println(">>> PÁGINA ATUAL: " + livro.getPagAtual() + " de " + livro.getTotPaginas() + " <<<");

            displayProgressBar(livro.getPagAtual(), livro.getTotPaginas());
            System.out.println("--------------------------------------------------");

            if (statusMessage != null && !statusMessage.isEmpty()) {
                System.out.println(statusMessage);
                System.out.println("--------------------------------------------------");
                statusMessage = "";
            }

            System.out.println("[1] Avançar 1 página  (->)");
            System.out.println("[2] Voltar 1 página   (<-)");
            System.out.println("[3] Folhear (ir para página específica)");
            System.out.println("[4] " + (livro.getAberto() ? "Fechar livro" : "Abrir livro"));
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
                    if (!livro.getAberto()) {
                        statusMessage = "⚠️ O livro está FECHADO! Abra o livro na opção [4] para avançar páginas.";
                    } else if (livro.getPagAtual() >= livro.getTotPaginas()) {
                        statusMessage = "🛑 DELIMITAÇÃO: Você já está na última página (" + livro.getTotPaginas() + " de " + livro.getTotPaginas() + ")! Não é possível avançar mais.";
                    } else {
                        livro.avancarPag();
                        statusMessage = "⏩ Você avançou para a página " + livro.getPagAtual() + ".";
                    }
                    break;

                case "2":
                    if (!livro.getAberto()) {
                        statusMessage = "⚠️ O livro está FECHADO! Abra o livro na opção [4] para voltar páginas.";
                    } else if (livro.getPagAtual() <= 0) {
                        statusMessage = "🛑 DELIMITAÇÃO: Você já está no início do livro (página 0 de " + livro.getTotPaginas() + ")! Não é possível voltar mais.";
                    } else {
                        livro.voltarPag();
                        statusMessage = "⏪ Você retornou para a página " + livro.getPagAtual() + ".";
                    }
                    break;

                case "3":
                    if (!livro.getAberto()) {
                        statusMessage = "⚠️ O livro está FECHADO! Abra o livro antes de folhear.";
                    } else {
                        System.out.print("Digite o número da página (0 a " + livro.getTotPaginas() + "): ");
                        String pageInput = readLine(scanner);
                        if (pageInput == null) {
                            break;
                        }
                        try {
                            int page = Integer.parseInt(pageInput);
                            livro.folhear(page);
                            if (page > livro.getTotPaginas()) {
                                statusMessage = "🛑 DELIMITAÇÃO (Slide 37): A página " + page + " ultrapassa o total (" + livro.getTotPaginas() + ")!\n   -> Regra aplicada: a página atual foi zerada para 0.";
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
                    if (livro.getAberto()) {
                        livro.fechar();
                        statusMessage = "📕 Você fechou o livro.";
                    } else {
                        livro.abrir();
                        statusMessage = "📖 Você abriu o livro.";
                    }
                    break;

                case "5":
                    System.out.println("\n--- DETALHES DO LIVRO ---");
                    System.out.println(livro.detalhes());
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
    // UTILITÁRIOS DE ENTRADA
    // ==========================================

    // Lê uma linha; retorna null se a entrada terminou (fim do input).
    private static String readLine(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine().trim();
    }

    // Pergunta um texto não vazio até receber um valor válido. Retorna null se cancelar.
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

    // Pergunta um inteiro >= min até receber um valor válido. Retorna null se cancelar.
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
