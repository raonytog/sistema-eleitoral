import java.time.Period;
import java.util.LinkedList;
import java.util.List;

public class Partido {
    private final int numero;
    private final String sigla;
    private final int federacao;

    private final List<Candidato> candidatos = new LinkedList<>();

    private int totalEleitos;
    private int votosNominais;
    private int votosLegenda;

    private Candidato maisVotado;
    private Candidato menosVotado;


    /**
     * Cria um partido a partir de seu numero para votação, sua sigla e um numero de federaçao
     * @param numero número de votação
     * @param sigla sigla do partido
     * @param federacao número da federação participante
     */
    public Partido(int numero, String sigla, int federacao) {
        this.numero = numero;
        this.sigla = sigla;
        this.federacao = federacao;
    }

    /**
     * Pre condição: Partido existente
     * @return Retorna o numero de votacao para o partido
     */
    public int getNumero() {
        return this.numero;
    }

    /**
     * Pre condição: Partido existente
     * @return Retorna a sigla do partido
     */
    public String getSigla() {
        return this.sigla;
    }

    /**
     * Pre condição: Partido existente
     * @return Retorna um numero de federação > 0 caso o partido pertença a alguma federação
     */
    public int getFederacao() {
        return this.federacao;
    }

    /**
     * Pre condição: Partido existente
     * @return Obtem uma cópia dos candidatos presentes no partido
     */
    public List<Candidato> getCandidatos() {
        List<Candidato> copy = new LinkedList<>(this.candidatos);
        return copy;
    }

    /**
     * Pre condição: Partido existente
     * @return Retorna numero total vereadores eleitos no partido
     */
    public int getTotalEleitos() {
        return this.totalEleitos;
    }

    /**
     * Pre condição: Partido existente
     * @return Retorna numero total votos nominais recorrentes no partido
     */
    public int getVotosNominais() {
        return this.votosNominais;
    }

    /**
     * Pre condição: Partido existente
     * @return Retorna numero total votos de legenda recorrentes no partido
     */
    public int getVotosLegenda() {
        return this.votosLegenda;
    }

    /**
     * Pre condição: Partido existente
     * @return Retorna a soma dos votos nominais e de legenda do partido
     */
    public int getVotosTotais() {
        return this.votosLegenda + votosNominais;
    }

    /**
     * Pre condição: Partido existente
     * @return Retorna o candidato mais votado do partido
     */
    public Candidato getMaisVotado() {
        return this.maisVotado;
    }

    /**
     * Pre condição: Partido existente
     * 
     * Seta o candidato como o mais votado do partido
     * @param c Candidato mais votado
     */
    public void setMaisVotado(Candidato c) {
        this.maisVotado = c;
    }

    /**
     * Pre condição: Partido existente
     * Retorna o candidato menos votado do partido
     */
    public Candidato getMenosVotado() {
        return this.menosVotado;
    }

    /**
     * Pre condição: Partido existente
     * 
     * Seta o candidato como o menos votado do partido
     * @param c Candidato menos votado
     */
    public void setMenosVotado(Candidato c) {
        this.menosVotado = c;
    }

    /**
     * Pre condição: Partido existente
     * 
     * Adiciona um candidato como mais ou menos votado, seguindo os criterios de:
     * numero de votos e mais velho.
     * 
     * Caso o candidato nao entre no mais ou menos votado, sai da funcao
     * @param candidato candidato a ser adicionado
     */
    public void addCandidato(Candidato candidato) {
        if (this.maisVotado == null && this.menosVotado == null) {
            setMaisVotado(candidato);
            setMenosVotado(candidato);
        }

        if (candidato.getVotos() > this.getMaisVotado().getVotos()) this.setMaisVotado(candidato);
        else if (candidato.getVotos() == this.getMaisVotado().getVotos()) {
            if (Period.between(candidato.getNascimento(), this.getMaisVotado().getNascimento()).getDays() > 0) this.setMaisVotado(candidato);
        }

        if (candidato.getVotos() < this.getMenosVotado().getVotos()) this.setMenosVotado(candidato);
        else if (candidato.getVotos() == this.getMenosVotado().getVotos()) {
            if (Period.between(candidato.getNascimento(), this.getMenosVotado().getNascimento()).getDays() < 0) this.setMenosVotado(candidato);
        }
    }

    /**
     * Pre condição: Partido existente
     * 
     * Aumenta a quantidade de votos de legenda do partido em 'qtdVotos'
     * @param qtdVotos quantidade de votos
     */
    public void somaVotosLegenda(int qtdVotos) {
        this.votosLegenda += qtdVotos;
    }

    /**
     * Pre condição: Partido existente
     * Aumenta a quantidade de votos de nominais do partido em 'qtdVotos'
     * @param qtdVotos quantidade de votos
     */
    public void somaVotosNominais(int qtdVotos) {
        this.votosNominais += qtdVotos;
    }

    /**
     * Pre condição: Partido existente
     * 
     * Incrementa o total de eleitos no partido
     * @param qtdVotos quantidade de votos
     */
    public void incrementaEleitos() {
        this.totalEleitos++;
    }

    @Override
    public String toString() {
        return sigla + " - " + numero;
    }
}
