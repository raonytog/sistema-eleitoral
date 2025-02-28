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

    private static final int ELEITO_QP = 2;
    private static final int ELEITO_MEDIA = 3;
    
    /**
     * @param nome Nome do candidato (nao necessita de ser o nome completo)
     * @param numero Numero de votacao do candidato
     * @param partido Partido ao qual o candidato pertence
     * @param nascimento Data de nascimento do candidat
     * @param eleito Situação de eleicão do candidato, se foi por média, legenda
     * @param genero Genero do candidato
     */
    public Candidato(String nome, int numero, Partido partido, LocalDate nascimento, int eleito, int genero) {
        this.nome = nome;
        this.numero = numero;
        this.partido = partido;
        this.nascimento = nascimento;
        this.eleito = eleito;
        this.genero = genero;

        if (this.eleito == 2 || this.eleito == 3)
            this.partido.incrementaEleitos();
    }


    /**
     * Pre condição: Candidato existente
     * @return Nome do candidato
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Pre condição: Candidato existente
     * @return Numero para votar do candidato
     */
    public int getNumero() {
        return this.numero;
    }

    /**
     * Pre condição: Candidato existente
     * @return Partido ao qual o candidato pertence
     */
    public Partido getPartido() {
        return this.partido;
    }  

    /**
     * Pre condição: Candidato existente
     * @return Numero de votação para o partido do candidato
     */
    public int getNumeroPartido() {
        return this.partido.getNumero();
    }

    /**
     * Pre condição: Candidato existente
     * @return Quantidade de votos obtidas pelo candidato
     */
    public int getVotos() {
        return this.votos;
    }

    /**
     * Pre condição: Candidato existente
     * @return Data de nascimento do candidato
     */
    public LocalDate getNascimento() {
        return this.nascimento;
    }

    /**
     * Pre condição: Candidato existente
     * @return Genero de identificação do candidato
     */
    public int getGenero() {
        return this.genero;
    }

    /**
     * Pre condição: Candidato existente
     * @return Situação de eleição do candidato: foi eleito, eleito por média, eleito por legenda
     */
    public int getEleito() {
        return this.eleito;
    }

    /**
     * Pre condição: Candidato existente
     * Aumenta o número de votos do candidato, além de somar os votos ao partido ao qual o mesmo pertence
     */
    public void somaVotos(int qtdVotos) {
        this.votos += qtdVotos;
        this.partido.somaVotosNominais(qtdVotos);
    }

    /**
     * Pre condição: Candidato existente
     * @return Idade do candidato (em anos)
     */
    public int getIdade(LocalDate diaAtual) {
        return Period.between(nascimento, diaAtual).getYears();
    }

    public boolean  getCandidatoFoiEleito() {
        return this.getEleito() == Candidato.ELEITO_MEDIA || this.getEleito() == Candidato.ELEITO_QP;
    }

    @Override
    public String toString() {
        NumberFormat brFormat = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));

        if (this.partido.getFederacao() > 0) return "*" + nome + " (" + this.partido.getSigla() + ", " + brFormat.format(votos) + " votos)";
        return nome + " (" + this.partido.getSigla() + ", " + brFormat.format(votos) + " votos)";
    }
}

