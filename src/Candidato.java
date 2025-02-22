import java.time.LocalDate;

public class Candidato {
    private String nome;
    private int numero;
    private Partido partido;

    private int votos;

    private LocalDate nascimento;

    private int genero;
    private int eleito;
    
    public Candidato(String nome, int numero, Partido partido, LocalDate nascimento, int eleito, int genero) {
        this.nome = nome;
        this.numero = numero;
        this.partido = partido;
        partido.addCandidato(this);
        this.nascimento = nascimento;
        this.eleito = eleito;
        this.genero = genero;
    }

    public String getNome() {
        return nome;
    }

    public int getNumero() {
        return numero;
    }

    public Partido getPartido() {
        return partido;
    }

    public int getVotos() {
        return votos;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public int getGenero() {
        return genero;
    }

    public int getEleito() {
        return eleito;
    }

    public void somaVotos(int qtdVotos) {
        this.votos += qtdVotos;
        this.partido.somaVotosNominais(qtdVotos);
    }

    @Override
    public String toString() {
        return nome + " " + numero + " VOTOS: " + votos;
    }
}

