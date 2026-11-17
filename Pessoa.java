public class Pessoa {
    private String nome;
    private int idade;
    private String sexo;

    // Construtor com validação dos dados
    public Pessoa(String nome, int idade, String sexo) {
        this.nome = validarTexto(nome, "Nome");
        this.idade = validarIdade(idade);
        this.sexo = validarTexto(sexo, "Sexo");
    }

    public void fazerAniver() {
        this.idade++;
    }

    public void fazerAniversario() {
        fazerAniver();
    }

    // Métodos auxiliares de validação
    private static String validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " não pode ser vazio.");
        }
        return valor.trim();
    }

    private static int validarIdade(int idade) {
        if (idade < 0) {
            throw new IllegalArgumentException("Idade não pode ser negativa.");
        }
        return idade;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = validarTexto(nome, "Nome");
    }

    public int getIdade() {
        return this.idade;
    }

    public void setIdade(int idade) {
        this.idade = validarIdade(idade);
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = validarTexto(sexo, "Sexo");
    }

  
    public void haveBirthday() { fazerAniver(); }
    public String getName() { return getNome(); }
    public void setName(String name) { setNome(name); }
    public int getAge() { return getIdade(); }
    public void setAge(int age) { setIdade(age); }
    public String getGender() { return getSexo(); }
    public void setGender(String gender) { setSexo(gender); }
}
