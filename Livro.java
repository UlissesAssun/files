public class Livro implements Publicacao {
   
    private String titulo;
    private String autor;
    private int totPaginas;
    private int pagAtual;
    private boolean aberto;
    private Pessoa leitor;

    
    public Livro(String titulo, String autor, int totPaginas, Pessoa leitor) {
        this.titulo = validarTexto(titulo, "Título");
        this.autor = validarTexto(autor, "Autor");
        this.totPaginas = validarTotPaginas(totPaginas);
        this.leitor = leitor;
        this.aberto = false;
        this.pagAtual = 0;
    }

    public String detalhes() {
        return "Livro:" + "Título= " + this.titulo + "\n, Autor= "
                + this.autor + "\n, Total de Paginas= " + this.totPaginas
                + "\n, Página Atual= " + this.pagAtual + "\n, Aberto?= "
                + this.aberto + "\n, Leitor=" + (this.leitor != null ? this.leitor.getNome() : "")
                + " Idade:" + (this.leitor != null ? this.leitor.getIdade() : 0)
                + " Sexo:" + (this.leitor != null ? this.leitor.getSexo() : "");
    }

    @Override
    public void abrir() {
        this.setAberto(true);
    }

    @Override
    public void fechar() {
        this.setAberto(false);
    }

    @Override
    public void folhear(int p) {
        if (p > this.totPaginas || p < 0) {
            this.setPagAtual(0); // Fora do limite redefine para 0 (Slide 37)
        } else {
            this.setPagAtual(p);
        }
    }

    @Override
    public void avancarPag() {
        if (this.pagAtual < this.totPaginas) {
            this.setPagAtual(this.getPagAtual() + 1);
        }
    }

    @Override
    public void voltarPag() {
        if (this.pagAtual > 0) {
            this.setPagAtual(this.getPagAtual() - 1);
        }
    }

    // Métodos auxiliares de validação
    private static String validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " não pode ser vazio.");
        }
        return valor.trim();
    }

    private static int validarTotPaginas(int totPaginas) {
        if (totPaginas <= 0) {
            throw new IllegalArgumentException("O total de páginas deve ser maior que zero.");
        }
        return totPaginas;
    }

    // Getters e Setters (Slides 20 a 22)
    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = validarTexto(titulo, "Título");
    }

    public String getAutor() {
        return this.autor;
    }

    public void setAutor(String autor) {
        this.autor = validarTexto(autor, "Autor");
    }

    public int getTotPaginas() {
        return this.totPaginas;
    }

    public void setTotPaginas(int totPaginas) {
        validarTotPaginas(totPaginas);
        if (totPaginas < this.pagAtual) {
            throw new IllegalArgumentException("O total de páginas não pode ser menor que a página atual.");
        }
        this.totPaginas = totPaginas;
    }

    public int getPagAtual() {
        return this.pagAtual;
    }

    public void setPagAtual(int pagAtual) {
        if (pagAtual < 0 || pagAtual > this.totPaginas) {
            throw new IllegalArgumentException("A página atual deve estar entre 0 e " + this.totPaginas + ".");
        }
        this.pagAtual = pagAtual;
    }

    // Slide 21 usa explicitamente getAberto(), mantemos ambos para conformidade total
    public boolean getAberto() {
        return this.aberto;
    }

    public boolean isAberto() {
        return this.aberto;
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public Pessoa getLeitor() {
        return this.leitor;
    }

    public void setLeitor(Pessoa leitor) {
        this.leitor = leitor;
    }

    // Aliases em inglês para retrocompatibilidade
    public String details() { return detalhes(); }
    public void open() { abrir(); }
    public void close() { fechar(); }
    public void browse(int p) { folhear(p); }
    public void advancePage() { avancarPag(); }
    public void backPage() { voltarPag(); }
    public String getTitle() { return getTitulo(); }
    public void setTitle(String t) { setTitulo(t); }
    public String getAuthor() { return getAutor(); }
    public void setAuthor(String a) { setAutor(a); }
    public int getTotalPages() { return getTotPaginas(); }
    public void setTotalPages(int tp) { setTotPaginas(tp); }
    public int getCurrentPage() { return getPagAtual(); }
    public void setCurrentPage(int pa) { setPagAtual(pa); }
    public boolean isOpen() { return getAberto(); }
    public void setOpen(boolean op) { setAberto(op); }
    public Pessoa getReader() { return getLeitor(); }
    public void setReader(Pessoa l) { setLeitor(l); }
}
