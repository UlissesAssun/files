public class Book implements Publication {
    private String title;
    private String author;
    private int totalPages;
    private int currentPage;
    private boolean open;
    private Person reader;

    public Book(String title, String author, int totalPages, Person reader) {
        this.title = validateText(title, "Title");
        this.author = validateText(author, "Author");
        this.totalPages = validateTotalPages(totalPages);
        this.reader = reader;
        this.open = false;
        this.currentPage = 0;
    }

    public String details() {
        return "Livro:" + "Título= " + this.title + "\n, Autor= "
                + this.author + "\n, Total de Paginas= " + this.totalPages
                + "\n, Página Atual= " + this.currentPage + "\n, Aberto?= "
                + this.open + "\n, Leitor=" + (this.reader != null ? this.reader.getName() : "")
                + " Idade:" + (this.reader != null ? this.reader.getAge() : 0)
                + " Sexo:" + (this.reader != null ? this.reader.getGender() : "");
    }

    @Override
    public void open() {
        this.setOpen(true);
    }

    @Override
    public void close() {
        this.setOpen(false);
    }

    @Override
    public void browse(int page) {
        if (page > this.totalPages || page < 0) {
            this.setCurrentPage(0);
        } else {
            this.setCurrentPage(page);
        }
    }

    @Override
    public void advancePage() {
        if (this.currentPage < this.totalPages) {
            this.setCurrentPage(this.getCurrentPage() + 1);
        }
    }

    @Override
    public void backPage() {
        if (this.currentPage > 0) {
            this.setCurrentPage(this.getCurrentPage() - 1);
        }
    }

    private static String validateText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " cannot be empty.");
        }
        return value.trim();
    }

    private static int validateTotalPages(int totalPages) {
        if (totalPages <= 0) {
            throw new IllegalArgumentException("Total pages must be greater than zero.");
        }
        return totalPages;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = validateText(title, "Title");
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = validateText(author, "Author");
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        validateTotalPages(totalPages);
        if (totalPages < this.currentPage) {
            throw new IllegalArgumentException("Total pages cannot be less than the current page.");
        }
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage < 0 || currentPage > this.totalPages) {
            throw new IllegalArgumentException("The current page must be between 0 and " + this.totalPages + ".");
        }
        this.currentPage = currentPage;
    }

    public boolean getOpen() {
        return this.open;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Person getReader() {
        return this.reader;
    }

    public void setReader(Person reader) {
        this.reader = reader;
    }
}
