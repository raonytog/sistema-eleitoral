import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;

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
        this.nascimento = nascimento;
        this.eleito = eleito;
        this.genero = genero;

        if (this.eleito == 2 || this.eleito == 3) this.partido.incrementaEleitos();; 
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

    public int getNumeroPartido() {
        return this.partido.getNumero();
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

    public int getIdade(LocalDate diaAtual) {
        return Period.between(nascimento, diaAtual).getYears();
    }

    @Override
    public String toString() {
        NumberFormat brFormat = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));

        if (this.partido.getFederacao() > 0) return "*" + nome + " (" + this.partido.getSigla() + ", " + brFormat.format(votos) + " votos)";
        return nome + " (" + this.partido.getSigla() + ", " + brFormat.format(votos) + " votos)";
    }
}

