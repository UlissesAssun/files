public class Main {
    public static void main(String[] args) {
        // Suporte ao modo terminal interativo
        if (args.length > 0 && (args[0].equalsIgnoreCase("--interactive") || args[0].equalsIgnoreCase("-i"))) {
            TerminalInterface.main(args);
            return;
        }

        // Testes Integrados Automatizados (Referência do Professor - Slides 33 a 38)
        Pessoa[] p = new Pessoa[2]; // vetor de pessoas
        Livro[] l = new Livro[3];   // vetor de livros

        p[0] = new Pessoa("Marcos", 21, "M");
        p[1] = new Pessoa("Maria", 25, "F");

        l[0] = new Livro("POO na prática", "Allan Stieg", 300, p[0]);
        l[1] = new Livro("Web Designer", "Marcos Pasquim", 500, p[1]);
        l[2] = new Livro("A volta sem ir", "Jonas Confuso", 800, p[0]);

        // Teste 1 (Slide 35): Detalhes iniciais do livro
        System.out.println("=== TESTE 1: DETALHES INICIAIS DO LIVRO (SLIDE 35) ===");
        System.out.println(l[0].detalhes());
        System.out.println();

        // Teste 2 (Slides 36 e 37): Abrir livro e folhear além do limite (400 > 300 páginas)
        System.out.println("=== TESTE 2: ABRIR E FOLHEAR ALÉM DO LIMITE (SLIDE 37) ===");
        l[0].abrir();
        l[0].folhear(400); // redefine para 0 conforme Slide 37
        System.out.println(l[0].detalhes());
        System.out.println();

        // Teste 3 (Slide 38): Navegação com folhear(200), avançar 2 vezes e voltar 1 vez
        System.out.println("=== TESTE 3: NAVEGAÇÃO DE PÁGINAS (SLIDE 38) ===");
        l[0].abrir();
        l[0].folhear(200);
        l[0].avancarPag();
        l[0].avancarPag();
        l[0].voltarPag();
        System.out.println(l[0].detalhes());
        System.out.println();

        // Teste 4: Limites de páginas ao avançar/voltar
        System.out.println("=== TESTE 4: LIMITES DE PÁGINAS ===");
        l[1].abrir();
        l[1].voltarPag();
        System.out.println("Voltar na página 0 (esperado 0): " + l[1].getPagAtual());
        l[1].folhear(500);
        l[1].avancarPag();
        System.out.println("Avançar na última página (esperado 500): " + l[1].getPagAtual());
        System.out.println();

        // Teste 5: Método fazerAniver
        System.out.println("=== TESTE 5: ANIVERSÁRIO (FAZERANIVER) ===");
        p[0].fazerAniver();
        System.out.println("Idade de Marcos (esperado 22): " + p[0].getIdade());
        System.out.println();

        // Teste 6: Cadastro de novo livro e validação de dados inválidos
        System.out.println("=== TESTE 6: NOVO LIVRO E VALIDAÇÃO ===");
        Pessoa novoLeitor = new Pessoa("Ana", 30, "F");
        Livro novoLivro = new Livro("Java Essencial", "Ana Souza", 250, novoLeitor);
        System.out.println(novoLivro.detalhes());
        try {
            new Livro("", "Alguém", 100, novoLeitor);
        } catch (IllegalArgumentException e) {
            System.out.println("Título vazio rejeitado: " + e.getMessage());
        }
        try {
            new Livro("Livro Inválido", "Alguém", 0, novoLeitor);
        } catch (IllegalArgumentException e) {
            System.out.println("Total de páginas zero rejeitado: " + e.getMessage());
        }
        try {
            novoLivro.setPagAtual(999);
        } catch (IllegalArgumentException e) {
            System.out.println("Página fora do intervalo rejeitada: " + e.getMessage());
        }
    }
}
