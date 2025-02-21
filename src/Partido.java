public class Partido {
    private int     numero;
    private String  sigla;
    private int     federacao;

    /** Candidados, como armazenar? */

    private int     totalVotos;
    private int     totalEeitos;
    private int     totalLegenda;
    private Candidato maisVotado;
    private Candidato menosVotado;

    public Partido(int numero, String sigla, int federacao) {
        this.numero = numero;
        this.sigla = sigla;
        this.federacao = federacao;
    }
}
