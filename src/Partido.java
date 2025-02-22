import java.util.LinkedList;
import java.util.List;

public class Partido {
    private int     numero;
    private String  sigla;
    private int     federacao;

    private List<Candidato> candidatos = new LinkedList<>();

    private int totalEleitos;
    private int votosNominais;
    private int votosLegenda;
    private Candidato maisVotado;
    private Candidato menosVotado;

    public Partido(int numero, String sigla, int federacao) {
        this.numero = numero;
        this.sigla = sigla;
        this.federacao = federacao;
    }

    public void addCandidato(Candidato candidato) {
        this.candidatos.add(candidato);
    }

    public void somaVotosLegenda(int qtdVotos) {
        this.votosLegenda += qtdVotos;
    }

    public void somaVotosNominais(int qtdVotos) {
        this.votosNominais += qtdVotos;
    }

    @Override
    public String toString() {
        int totais = votosLegenda + votosNominais;

        return sigla + " " + numero + " TOTAIS:" + totais + " LEGENDA: " + votosLegenda + " NOMINAIS: " + votosNominais + " " + this.candidatos;
    }
}
