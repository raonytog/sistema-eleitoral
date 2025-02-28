import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Relatorios {
    private final SistemaEleitoral sistema;

    /**
     * @param sistema Inicializa o relatorio com o sistema eleitoral para manipulação dos dados
     * @return Relatorio criado
     */
    public Relatorios(SistemaEleitoral sistema) {
        this.sistema = sistema;
    }

    /**
     * @return Obtem o sistema eleitoral
     */
    public SistemaEleitoral getSistemaEleitoral() { 
        return this.sistema;
    }

    /**
     * Imprime o numero de vagas disponíveis para eleição, no caso dos vereadores
     */
    public void imprimeNumeroDeVagas() {
        System.out.println("Número de vagas: " + this.getSistemaEleitoral().getQtdEleitos());
    }

    /**
     * Imprime os vereadores eleitos no formato:
     * Caso faça parte de um partido de de federação, o mesmo será impresso com o "*" anterior ao nome
     * <indice> - <nome> (<sigla>, <quantidade de votos> votos)
     */
    public void imprimeVereadoresEleitos() {
        System.out.println("Vereadores eleitos:");

        int i = 1;
        for (Candidato candidato : this.getSistemaEleitoral().getCandidatosEleitos()) {
            if (i > this.getSistemaEleitoral().getQtdEleitos()) break;
            System.out.print(i + " - ");
            System.out.println(candidato);
            i++;
        }
    }

    /**
    * Imprime os candidatos mais votados em ordem decrescente pelo número de vagas.
    * Em caso de empate, o candidato mais novo entra
    */
    public void imprimeCandidatosMaisVotados() {
        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");

        List<Candidato> maisVotados = this.getSistemaEleitoral().ordenaCandidatos();
        int i = 1;
        for (Candidato candidato : maisVotados) {
            if (i > this.getSistemaEleitoral().getQtdEleitos()) break;
            System.out.print(i + " - ");
            System.out.println(candidato);
            i++;
        }
    }

    /**
     * Imprime os candidatos que seriam eleitos caso a votação fosse majoritária,
     * juntamente de sua posição no rankin dos mais votados
     */
    public void imprimeSeriamEleitos() {
        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
        System.out.println("(com sua posição no ranking de mais votados)");
        List<Candidato> maisVotados = this.getSistemaEleitoral().ordenaCandidatos();

        int i = 1;
        for (Candidato candidato : maisVotados) {
            if (i > this.getSistemaEleitoral().getQtdEleitos()) break;

            if (candidato.getCandidatoFoiEleito() == false) {
                System.out.print(i + " - ");
                System.out.println(candidato);
            }
            i++;
        }
    }

    /**
     * Imprime os vereadores que foram eleitos por beneficio do sistema proporcional
     */
    public void imprimeEleitosBeneficiados() {
        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:");
        System.out.println("(com sua posição no ranking de mais votados)");

        List<Candidato> maisVotados = this.getSistemaEleitoral().ordenaCandidatos();
        int i = 1;
        for (Candidato candidato : maisVotados) {
            if (i > this.getSistemaEleitoral().getQtdEleitos() && candidato.getCandidatoFoiEleito()) {
                System.out.print(i + " - ");
                System.out.println(candidato);
            }
            i++;
        }
    }

    /**
     * Imprime os partidos mais votados seguindo o formato:
     * <indice> - <silga> - <numero>, <votos totais> (<votos nominais> e <votos de legenda>), <total de eleitos> candidatos eleitos 
     */
    public void imprimePartidosMaisVotados() {
        System.out.println("Votação dos partidos e número de candidatos eleitos:");

        NumberFormat brFormat = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        List<Partido> maisVotados = this.getSistemaEleitoral().ordenaPartidos();
        int i = 1;
        for (Partido partido : maisVotados) {
            String out;
            int v = partido.getVotosTotais();
            if (v > 1) out = i + " - " + partido + ", " + brFormat.format(partido.getVotosTotais()) + " votos ";
            else out = i + " - " + partido + ", " + brFormat.format(partido.getVotosTotais()) + " voto ";

            v = partido.getVotosNominais();
            if (v > 1) out += "(" + brFormat.format(partido.getVotosNominais()) + " nominais e "; 
            else out += "(" + brFormat.format(partido.getVotosNominais()) + " nominal e "; 

            out += brFormat.format(partido.getVotosLegenda()) + " de legenda), ";
            out += partido.getTotalEleitos();

            if (partido.getTotalEleitos() > 1) out += " candidatos eleitos";
            else out += " candidato eleito";

            System.out.println(out);
            i++;
        }
    }

    /**
     * Imprime os candidatos mais votados de cada partido no seguinte formato:
     * <indice> - <sigla> - <nome do partido>, <nome do candidato> (<numero>, <votos>) / <nome do candidato> (<numero>, <votos>))
     */
    public void imprimeExtremosDosPartidos() {
        System.out.println("Primeiro e último colocados de cada partido:");

        NumberFormat brFormat = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        List<Partido> maisVotados = this.getSistemaEleitoral().ordenaPartidosPorCandidato();
        int i = 1;
        for (Partido partido : maisVotados) {
            if (partido.getMaisVotado() == null || partido.getMenosVotado() == null || partido.getVotosNominais() == 0) continue;

            String out = i + " - " + partido + ", ";
            
            out += partido.getMaisVotado().getNome() + " (" + partido.getMaisVotado().getNumero() + ", ";
            int v = partido.getMaisVotado().getVotos();
            if (v > 1) out += brFormat.format(partido.getMaisVotado().getVotos()) + " votos) / ";
            else out += brFormat.format(partido.getMaisVotado().getVotos()) + " voto) / ";

            out += partido.getMenosVotado().getNome() + " (" + partido.getMenosVotado().getNumero() + ", ";
            v = partido.getMenosVotado().getVotos();
            if (v > 1) out += brFormat.format(partido.getMenosVotado().getVotos()) + " votos)";
            else out += brFormat.format(partido.getMenosVotado().getVotos()) + " voto)";

            System.out.println(out);
            i++;
        }
    }

    /**
     * Imprime a distribuição de idades dos candidatos e suas participações em porcentagem
     */
    public void imprimeEleitosPorIdade() {
        int total = this.getSistemaEleitoral().getCandidatosEleitos().size();

        int idade, menorQue30 = 0, menorQue40 = 0, menorQue50 = 0, menorQue60 = 0, demais = 0;
        for (Candidato c: this.getSistemaEleitoral().getCandidatosEleitos()) {
            idade = c.getIdade(this.getSistemaEleitoral().getDiaVotacao());
            if (idade < 30) menorQue30++;
            else if (idade < 40) menorQue40++;
            else if (idade < 50) menorQue50++;
            else if (idade < 60) menorQue60++;
            else demais++;
        }

        NumberFormat brFormat = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        brFormat.setMaximumFractionDigits(2);
        brFormat.setMinimumFractionDigits(2);

        System.out.println("Eleitos, por faixa etária (na data da eleição):");
        System.out.println("       Idade < 30: " + menorQue30 + " (" + brFormat.format(100.0 * menorQue30 / total) + "%)");
        System.out.println(" 30 <= Idade < 40: " + menorQue40 + " (" + brFormat.format(100.0 * menorQue40 / total) + "%)");
        System.out.println(" 40 <= Idade < 50: " + menorQue50 + " (" + brFormat.format(100.0 * menorQue50 / total) + "%)");
        System.out.println(" 50 <= Idade < 60: " + menorQue60 + " (" + brFormat.format(100.0 * menorQue60 / total) + "%)");
        System.out.println(" 60 <= Idade     : " + demais + " (" + brFormat.format(100.0 * demais / total) + "%)");
    }

    /**
     * Imprime a porcentagem de participação masculina e feminina na eleição
     */
    public void imprimeEleitosPorGenero() {
        int total = this.getSistemaEleitoral().getCandidatosEleitos().size();
        int mas = 0, fem = 0;
        for (Candidato c: this.getSistemaEleitoral().getCandidatosEleitos()) {
            if (c.getGenero() == 2) mas++;
            else fem++;
        }

        NumberFormat brFormat = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        brFormat.setMaximumFractionDigits(2);
        brFormat.setMinimumFractionDigits(2);

        System.out.println("Eleitos, por gênero:");
        System.out.println("Feminino:  " + fem + " (" + brFormat.format(100.0 * fem / total) + "%)");
        System.out.println("Masculino: " + mas + " (" + brFormat.format(100.0 * mas / total) + "%)");
    }

    /**
     * Imprime a quantidade e porcentagem de cada tipo de voto 
     */
    public void imprimePorcentagensDeVoto() {
        int total = this.getSistemaEleitoral().getVotosNominais() + this.getSistemaEleitoral().getVotosLegenda();

        NumberFormat votos = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        NumberFormat porcentagem = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        porcentagem.setMaximumFractionDigits(2);
        porcentagem.setMinimumFractionDigits(2);

        System.out.println("Total de votos válidos:    " + votos.format(total));
        System.out.println("Total de votos nominais:   " + votos.format(this.getSistemaEleitoral().getVotosNominais()) + " (" + porcentagem.format(100.0 * this.getSistemaEleitoral().getVotosNominais() / total) + "%)");
        System.out.println("Total de votos de legenda: " + votos.format(this.getSistemaEleitoral().getVotosLegenda()) + " (" + porcentagem.format(100.0 * this.getSistemaEleitoral().getVotosLegenda() / total) + "%)");
    }
}