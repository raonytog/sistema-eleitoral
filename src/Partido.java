import java.util.LinkedList;
import java.util.List;

public class Partido {
    private int numero;
    private String sigla;
    private int federacao;

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

    public int getNumero() {
        return numero;
    }

    public String getSigla() {
        return sigla;
    }

    public int getFederacao() {
        return federacao;
    }

    public List<Candidato> getCandidatos() {
        return candidatos;
    }

    public int getTotalEleitos() {
        return totalEleitos;
    }

    public int getVotosNominais() {
        return votosNominais;
    }

    public int getVotosLegenda() {
        return votosLegenda;
    }

    public Candidato getMaisVotado() {
        return maisVotado;
    }

    public void setMaisVotado(Candidato c) {
        this.maisVotado = c;
    }

    public Candidato getMenosVotado() {
        return menosVotado;
    }

    public void setMenosVotado(Candidato c) {
        this.menosVotado = c;
    }

    public void addCandidato(Candidato candidato) {
        if (this.maisVotado == null && this.menosVotado == null) {
            setMaisVotado(candidato);
            setMenosVotado(candidato);
            return;
        }

        if (candidato.getVotos() > this.getMaisVotado().getVotos()) this.setMaisVotado(candidato);
        else if (candidato.getVotos() < this.getMaisVotado().getVotos()) this.setMenosVotado(candidato);
        // this.candidatos.add(candidato);
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
